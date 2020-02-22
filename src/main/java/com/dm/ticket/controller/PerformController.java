package com.dm.ticket.controller;

import com.dm.ticket.model.PageData;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.PerformDetailDto;
import com.dm.ticket.model.dto.PerformDto;
import com.dm.ticket.model.dto.PerformThumbnail;
import com.dm.ticket.model.entity.City;
import com.dm.ticket.model.entity.Perform;
import com.dm.ticket.service.PerformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;


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
    @ApiOperation("新增演出（成功后调用新增时间与tickets）")
    public StrResponseData addShow(@Valid @RequestBody PerformDto dto) {
        Map<String, Long> map = performService.addPerform(dto);
        if (map.containsKey("success")) {
            return successResponse(String.valueOf(map.get("success")));
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除演出（需调用时间、票务同时删除）")
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
    @ApiOperation("通过id获取演出详情（包含时间与票务）")
    public Object getPerformDetail(@PathVariable Long id) {
        PerformDetailDto perform = performService.getPerformById(id);
        if (perform != null) {
            return perform;
        }else {
            return errorResponse("查询失败");
        }
    }

    @PostMapping("/all/{pageNum}")
    @ApiOperation("获取演出缩略列表（含分页，每页30条，起始页为1，超出页数返回第一页数据）")
    public Object getAllPerformByPage(@PathVariable Long pageNum) {
        PageData<List<PerformThumbnail>> pageData = performService.getAllPerformByPage(pageNum);
        if (pageData != null && pageData.getData() != null) {
            return pageData;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/{categoryId}")
    @ApiOperation("通过类别id获取近期7场演出")
    public Object getRecommend(@PathVariable Integer categoryId) {
        List<PerformThumbnail> performList = performService.getRecommendList(categoryId);
        if (performList != null) {
            return performList;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/search/{str}")
    @ApiOperation("搜索演出（明星/演出名字）（含分页，每页30条，起始页为1，超出页数返回第一页数据）")
    public Object searchPerform(@PathVariable Long pageNum,
                                @NotBlank @PathVariable String str) {
        PageData<List<PerformThumbnail>> pageData = performService.searchPerform(pageNum, str);
        if (pageData != null && pageData.getData() != null) {
            return pageData;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/city/{name}")
    @ApiOperation("通过演出名字获取所有演出城市")
    public Object getCitiesByName(@PathVariable String name) {
        Map<Long, City> map = performService.getCitiesByName(name);
        if (!map.isEmpty()) {
            return map;
        }else {
            return errorResponse("获取失败");
        }
    }
}
