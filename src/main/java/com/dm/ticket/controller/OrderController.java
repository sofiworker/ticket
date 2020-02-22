package com.dm.ticket.controller;

import com.dm.ticket.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(produces = "application/json", tags = "用户订单")
public class OrderController extends BaseController {

    private OrderService service;

    @Autowired
    public void setService(OrderService service) {
        this.service = service;
    }
}
