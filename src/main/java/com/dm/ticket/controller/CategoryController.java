package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.CategoryDto;
import com.dm.ticket.model.entity.Category;
import com.dm.ticket.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/category")
@Api(produces = "application/json", tags = "演出类别")
public class CategoryController extends BaseController {

    private CategoryService service;

    @Autowired
    public void setService(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有类别")
    public Object getAll() {
        List<Category> categoryList = service.getAllCategory();
        if (categoryList != null){
            return categoryList;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/exist/{name}")
    @ApiOperation("查询类别是否存在")
    public StrResponseData isExist(@NotBlank @PathVariable String name){
        if (service.isExist(name)) {
            return successResponse("类别未存在");
        }else {
            return errorResponse("类别已存在");
        }
    }

    @PostMapping("/add")
    @ApiOperation("新增类别")
    public StrResponseData addNewCategory(@Valid @RequestBody CategoryDto dto) {
        if (service.addNewCategory(dto)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/search/{name}")
    @ApiOperation("搜索类别")
    public Object searchCategory(@NotBlank @PathVariable String name) {
        List<Category> categories = service.searchCategory(name);
        if (categories != null) {
            return categories;
        }else {
            return errorResponse("搜索失败");
        }
    }
}
