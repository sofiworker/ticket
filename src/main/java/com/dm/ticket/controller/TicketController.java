package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.TicketDto;
import com.dm.ticket.model.entity.Ticket;
import com.dm.ticket.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@Api(produces = "application/json", tags = "演出票价（在设置演出时间后调用）")
public class TicketController extends BaseController {

    private TicketService service;

    @Autowired
    public void setService(TicketService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ApiOperation("新增演出票价")
    public StrResponseData addNewTicket(@Valid @RequestBody TicketDto dto) {
        if (service.addNewTicket(dto)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/detail/{timeId}")
    @ApiOperation("通过timeId获取票务详情")
    public Object getTicketByTimeId(@PathVariable Long timeId) {
        List<Ticket> tickets = service.getTicketDetail(timeId);
        if (tickets != null) {
            return tickets;
        }else {
            return errorResponse("获取失败");
        }
    }
}
