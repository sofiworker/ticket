package com.dm.ticket.controller;

import com.dm.ticket.model.PageData;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.entity.Perform;
import com.dm.ticket.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/perform")
@Api(produces = "application/json", tags = "演出")
public class PerformController extends BaseController {

    private PerformService performService;

    @Autowired
    public void setPerformService(PerformService performService) {
        this.performService = performService;
    }

    @PostMapping("/add")
    @ApiOperation("新增演出")
    public StrResponseData addShow(@Valid @RequestBody PerformDto dto) {
        if (performService.addPerform(dto)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除演出")
    public StrResponseData deletePerform(@PathVariable Long id) {
        if (performService.deletePerform(id)) {
            return successResponse("删除成功");
        }else {
            return errorResponse("删除失败");
        }
    }

    @PostMapping("/modify")
    @ApiOperation("修改演出的具体事项")
    public StrResponseData modify(@Valid @RequestBody Perform perform) {
        if (performService.modify(perform)) {
            return successResponse("修改成功");
        }else {
            return errorResponse("修改失败");
        }
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("通过id查询演出")
    public Object getPerformDetail(@PathVariable Long id) {
        Perform perform = performService.getPerformById(id);
        if (perform != null) {
            return perform;
        }else {
            return errorResponse("查询失败");
        }
    }

    @PostMapping("/all/{pageNum}")
    @ApiOperation("获取所有演出（含分页，每页30条，起始页为1，超出页数返回第一页数据）")
    public Object getAllPerformByPage(@PathVariable Long pageNum) {
        PageData<List<PerformDetailDto>> pageData = performService.getAllPerformByPage(pageNum);
        if (pageData != null && pageData.getData() != null) {
            return pageData;
        }else {
            return errorResponse("获取失败");
        }
    }
}
