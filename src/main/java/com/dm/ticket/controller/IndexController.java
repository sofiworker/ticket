package com.dm.ticket.controller;

import com.dm.ticket.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
@Api(produces = "application/json", tags = "首页")
public class IndexController extends BaseController {

    private IndexService service;

    @Autowired
    public void setService(IndexService service) {
        this.service = service;
    }

    @GetMapping("/banner")
    @ApiOperation("滚动横幅（未完成）")
    public Object getBanner() {
        return null;
    }
}
