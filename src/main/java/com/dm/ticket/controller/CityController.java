package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.City;
import com.dm.ticket.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/city")
@Api(produces = "application/json", tags = "演出城市控制器")
public class CityController extends BaseController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有城市")
    public Object getCityList() {
        List<City> cityList = cityService.getCityList();
        if (cityList != null) {
            return cityList;
        }else {
            return errorResponse("获取失败");
        }
    }

    @GetMapping("/exist")
    @ApiOperation("城市是否存在")
    public StrResponseData isExist(@NotBlank @RequestParam String cityName){
        if (cityService.isExist(cityName)) {
            return successResponse("城市未存在");
        }else {
            return errorResponse("城市已存在");
        }
    }

    @PostMapping("/add/{cityName}")
    @ApiOperation("新增城市（先判断是否存在）")
    public StrResponseData addNewCity(@NotBlank @PathVariable String cityName){
        if (cityService.addNewCity(cityName)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/{id}")
    @ApiOperation("通过id获取城市名称")
    public Object getNameById(@PathVariable Long id){
        City city = cityService.getNameById(id);
        if (city != null) {
            return city;
        }else {
            return errorResponse("获取失败");
        }
    }
}
