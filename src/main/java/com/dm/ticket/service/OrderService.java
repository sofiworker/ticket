package com.dm.ticket.service;


import com.alipay.api.AlipayApiException;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.OrderDto;
import com.dm.ticket.model.entity.OrderForPerform;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    Long addNewOrder(OrderDto dto);

    PageData<List<OrderForPerform>> getUserOrders(Long userId, Long pageNum);

    String payForOrder(Long orderId);

    OrderForPerform orderStatus(Long orderId);

    boolean paramVerify(HttpServletRequest request) throws AlipayApiException;

    void notifyParamVerify(HttpServletRequest request) throws AlipayApiException;

    boolean refundSuccess(Long orderId) throws AlipayApiException;
}
