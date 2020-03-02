package com.dm.ticket.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.config.AliPayConfig;
import com.dm.ticket.mapper.OrderMapper;
import com.dm.ticket.mapper.PerformMapper;
import com.dm.ticket.mapper.TicketMapper;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.OrderDto;
import com.dm.ticket.model.entity.OrderForPerform;
import com.dm.ticket.model.entity.Ticket;
import com.dm.ticket.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private TicketMapper ticketMapper;
    private OrderMapper orderMapper;
    private PerformMapper performMapper;
    private RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "test_dlx_exchange_name_order";
    private static final String ROUTING_KEY = "order.pay";
    private static AlipayClient alipayClient;
    private static AlipayTradePagePayRequest payRequest;

    @Autowired
    public void setTicketMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setPerformMapper(PerformMapper performMapper) {
        this.performMapper = performMapper;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    static {
        alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL, AliPayConfig.APP_ID,
                AliPayConfig.APP_PRIVATE_KEY, "json", AliPayConfig.CHARSET,
                AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.SIGN_TYPE);
        payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AliPayConfig.RETURN_URL);
        payRequest.setNotifyUrl(AliPayConfig.NOTIFY_URL);
    }

    @Override
    public Long addNewOrder(OrderDto dto) {
        OrderForPerform order = new OrderForPerform();
        BeanUtils.copyProperties(dto, order);
        Ticket ticket = ticketMapper.selectById(dto.getTicketId());
        order.setMoney(ticket.getMoney().multiply(new BigDecimal(dto.getCount())));
        if (ticket.getCount() >= dto.getCount()) {
            ticket.setCount(ticket.getCount()-dto.getCount());
            ticketMapper.updateById(ticket);
            orderMapper.insert(order);
            sendOrderInfo(order.getId());
            return order.getId();
        }else {
            return 0L;
        }
    }

    /**
     * 通过mq设置过期时间(1小时过期)
     * @param orderId 订单id
     */
    private void sendOrderInfo(Long orderId){
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            int expiration = 1000 * 60 * 60;
            messageProperties.setExpiration(String.valueOf(expiration));
            return message;
        };
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, String.valueOf(orderId), messagePostProcessor, new CorrelationData(UUID.randomUUID().toString()));
    }

    @RabbitListener(queues = "dead_letters_queue_name_order")
    public void handler(Message message, Channel channel) throws IOException, AlipayApiException {
        /*
         * 发送消息之前，根据订单ID去查询订单的状态，如果已支付不处理，如果未支付，则更新订单状态为取消状态。
         */
        byte[] body = message.getBody();
        Long orderId = Long.parseLong(new String(body, StandardCharsets.UTF_8));
        OrderForPerform order = orderMapper.selectById(orderId);

        if (order.getState() == 0) {
            //取消订单
            orderMapper.updateOrderStatus(orderId, 2);
            ticketMapper.addTicketByOrderStatus(order.getCount(), order.getTicketId());
            cancelAliOrder(orderId);
        }
        if(channel.isOpen()){
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private void cancelAliOrder(Long orderId) throws AlipayApiException {
        AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
        tradeQueryRequest.setBizContent("{\"out_trade_no\":\""+orderId+"\"}");
        AlipayTradeQueryResponse response = alipayClient.execute(tradeQueryRequest);
        if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {
            AlipayTradeCloseRequest tradeCloseRequest = new AlipayTradeCloseRequest();
            tradeCloseRequest.setBizContent("{\"out_trade_no\":\""+orderId+"\"}");
            alipayClient.execute(tradeCloseRequest);
        }
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public PageData<List<OrderForPerform>> getUserOrders(Long userId, Long pageNum) {
        Page<OrderForPerform> page = new Page<>(pageNum, 10);
        IPage<OrderForPerform> iPage = orderMapper.getUserOrders(page, userId);
        PageData<List<OrderForPerform>> pageData = new PageData<>();
        pageData.setTotal(iPage.getTotal()).setTotalPage(iPage.getPages()).setData(iPage.getRecords())
                .setCurPage(iPage.getCurrent());
        return pageData;
    }

    @Override
    public String payForOrder(Long orderId) {
        OrderForPerform order = orderMapper.selectById(orderId);
        String content =
                "{\"out_trade_no\":\"" +order.getId()+"\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "\"total_amount\":" +order.getMoney()+"," +
                "\"subject\":\"" +performMapper.selectById(order.getPerformId()).getTitle()+"\"}";
        payRequest.setBizContent(content);
        String form = "";
        try {
            form = alipayClient.pageExecute(payRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @Override
    public OrderForPerform orderStatus(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    public void notifyParamVerify(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = getRequestMap(request);
        if (paramVerify(request)) {
            orderMapper.updateOrderStatus(Long.parseLong(params.get("out_trade_no")), 1);
        }
    }

    @Override
    public boolean paramVerify(HttpServletRequest request) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(getRequestMap(request), AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
    }

    private Map<String, String> getRequestMap(HttpServletRequest request){
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String name : parameterMap.keySet()) {
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    @Override
    public boolean refundSuccess(Long orderId) throws AlipayApiException {
        // TODO: 2020/3/2 需要将订单数量回滚
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        OrderForPerform order = orderMapper.selectById(orderId);
        String content = "{\"out_trade_no\":\""+ orderId +"\","+
                "\"refund_amount\":" +order.getMoney()+
                "}";
        request.setBizContent(content);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        boolean success = response.isSuccess();
        if (success) {
            order.setState(3);
        }else {
            order.setState(4);
        }
        orderMapper.updateById(order);
        return success;
    }
}
