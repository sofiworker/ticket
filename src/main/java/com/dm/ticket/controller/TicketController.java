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
@Api(produces = "application/json", tags = "演出票价（在演出新增成功后调用）")
public class TicketController extends BaseController {

    private TicketService service;

    @Autowired
    public void setService(TicketService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ApiOperation("新增演出票价")
    public StrResponseData addNewTicket(@Valid @RequestBody List<TicketDto> dto) {
        if (service.addNewTicket(dto)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/detail/{performId}")
    @ApiOperation("通过performId获取票务列表")
    public Object getTicketByTimeId(@PathVariable Long performId) {
        List<Ticket> tickets = service.getTicketDetail(performId);
        if (tickets != null) {
            return tickets;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/delete/{performId}")
    @ApiOperation("通过performId删除票务列表")
    public StrResponseData deleteTickets(@PathVariable Long performId){
        if (service.deleteTickets(performId)) {
            return successResponse("删除成功");
        }else {
            return errorResponse("删除失败");
        }
    }
}
