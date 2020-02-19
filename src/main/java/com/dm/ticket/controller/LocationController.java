package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.Location;
import com.dm.ticket.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequestMapping("/location")
@Api(produces = "application/json", tags = "城市地点")
public class LocationController extends BaseController {

    private LocationService service;

    @Autowired
    public void setService(LocationService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ApiOperation("通过cityId获取该城市所有地址")
    public Object getAllById(@RequestParam Long id){
        List<Location> locationList = service.getAllLocation(id);
        if (locationList != null) {
            return locationList;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/exist/{cityId}/{detail}")
    @ApiOperation("判断地址是否存在")
    public StrResponseData detailIsExist(@PathVariable Long cityId,
                                         @NotBlank @PathVariable String detail) {
        if (service.isExist(cityId, detail)) {
            return errorResponse("地址已存在");
        }else {
            return successResponse("地址未存在");
        }
    }

    @PostMapping("/add/{cityId}/{detail}")
    @ApiOperation("新增地址（先判断是否存在）")
    public StrResponseData addNewDetail(@PathVariable Long cityId,
                                        @NotBlank @PathVariable String detail) {
        if (service.addNewDetail(cityId, detail)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/search/{cityId}/{detail}")
    @ApiOperation("搜索地址")
    public Object searchLocation(@PathVariable Long cityId,
                                 @NotBlank @PathVariable String detail) {
        List<Location> locations = service.searchLocation(cityId, detail);
        if (locations != null) {
            return locations;
        }else {
            return errorResponse("搜索失败");
        }
    }
}
