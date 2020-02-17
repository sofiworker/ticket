package com.dm.ticket.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.ShipDto;
import com.dm.ticket.model.entity.Ship;
import com.dm.ticket.service.ShipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description 用户收货地址
 */
@RestController
@RequestMapping("/ship")
@Api(produces = "{application/json}", tags = {"用户收货地址"})
public class ShipController extends BaseController {

    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping("/save")
    @ApiOperation("保存收货地址")
    public StrResponseData saveShip(@Valid @RequestBody Ship ship){
        if (!Validator.isMobile(ship.getPhone())) {
            return errorResponse("请输入正确的手机号");
        }
        if (shipService.saveShip(ship)) {
            return successResponse("保存成功");
        }else {
            return errorResponse("保存失败");
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新收货地址")
    public StrResponseData updateShip(@Valid @RequestBody ShipDto dto){
        if (!Validator.isMobile(dto.getPhone())) {
            return errorResponse("请输入正确的手机号");
        }
        if (shipService.updateShip(dto)) {
            return successResponse("更新成功");
        }else {
            return errorResponse("更新失败");
        }
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("通过id删除地址")
    public StrResponseData deleteById(@PathVariable Long id){
        if (shipService.deleteShip(id)) {
            return successResponse("删除成功");
        }else {
            return errorResponse("删除失败");
        }
    }


    @PostMapping("/list/{uid}")
    @ApiOperation("获取用户所有地址")
    public Object getShipList(@PathVariable Long uid){
        List<ShipDto> list = shipService.getShipList(uid);
        if (list != null) {
           return list;
        }else {
            return errorResponse("获取失败");
        }
    }
}
