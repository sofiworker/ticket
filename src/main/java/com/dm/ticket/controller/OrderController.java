package com.dm.ticket.controller;

import com.alipay.api.AlipayApiException;
import com.dm.ticket.config.AliPayConfig;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.OrderDto;
import com.dm.ticket.model.entity.OrderForPerform;
import com.dm.ticket.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(produces = "application/json", tags = "订单")
public class OrderController extends BaseController {

    private OrderService service;

    @Autowired
    public void setService(OrderService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ApiOperation("新增订单")
    public Object addNewOrder(@RequestBody OrderDto dto){
        if (dto.getCount() <= 0) {
            return errorResponse("票数有误");
        }
        Long i = service.addNewOrder(dto);
        if (i == 0) {
            return errorResponse("库存不足，新增失败");
        } else {
            return successResponse(String.valueOf(i));
        }
    }

    @PostMapping("/pay/{orderId}")
    @ApiOperation("订单支付")
    public void payOrder(@PathVariable Long orderId, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset="+AliPayConfig.CHARSET);
        response.getWriter().write(service.payForOrder(orderId));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @GetMapping("/return")
    @ApiOperation("支付宝同步通知")
    public StrResponseData aliPayReturn(HttpServletRequest request) throws AlipayApiException {
        if (service.paramVerify(request)) {
            return successResponse("同步成功");
        }else {
            return errorResponse("同步失败");
        }
    }

    @PostMapping("/notify")
    @ApiOperation("支付宝异步通知（前端无关）")
    public void aliPayNotify(HttpServletRequest request) throws AlipayApiException {
        service.notifyParamVerify(request);
    }

    @GetMapping("/user")
    @ApiOperation("获取用户订单，含分页，每页10条")
    public Object getUserOrders(@RequestParam Long userId, @RequestParam Long pageNum) {
        PageData<List<OrderForPerform>> orders = service.getUserOrders(userId, pageNum);
        if (orders != null && orders.getData() != null) {
            return orders;
        }else {
            return errorResponse("获取失败");
        }
    }

    @GetMapping("/status")
    @ApiOperation("获取订单状态")
    public Object getOrderStatus(@RequestParam Long orderId) {
        OrderForPerform order = service.orderStatus(orderId);
        if (order != null) {
            return order;
        }else {
            return errorResponse("查询失败");
        }
    }

    @GetMapping("/refund")
    @ApiOperation("用户退款")
    public StrResponseData userRefund(@RequestParam Long orderId) throws AlipayApiException {
        if (service.refundSuccess(orderId)) {
            return successResponse("退款成功");
        }else {
            return errorResponse("退款失败");
        }
    }
}
