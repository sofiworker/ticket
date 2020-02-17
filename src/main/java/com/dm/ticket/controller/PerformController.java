package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.Perform;
import com.dm.ticket.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/perform")
@Api(produces = "application/json", tags = "演出控制器")
public class PerformController extends BaseController {

    private PerformService performService;

    @Autowired
    public void setPerformService(PerformService performService) {
        this.performService = performService;
    }

    @PostMapping("/add")
    @ApiOperation("新增演出")
    public StrResponseData addShow(@Valid @RequestBody Perform perform) {
        if (performService.addPerform()) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }
}
