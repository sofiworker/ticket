package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.Banner;
import com.dm.ticket.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/banner")
@Api(produces = "application/json", tags = "主页banner")
public class BannerController extends BaseController {

    private BannerService service;

    @Autowired
    public void setService(BannerService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ApiOperation("新增banner")
    public StrResponseData addBanner(@Valid @RequestBody Banner banner){
        if (service.addBanner(banner)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @GetMapping("/")
    @ApiOperation("获取主页banner，3条")
    public Object getBannerList() {
        List<Banner> bannerList = service.getBannerList();
        if (bannerList != null) {
            return bannerList;
        }else {
            return errorResponse("获取失败");
        }
    }
}
