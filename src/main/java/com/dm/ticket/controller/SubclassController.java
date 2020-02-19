package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.Subclass;
import com.dm.ticket.service.SubclassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/subclass")
@Api(produces = "application/json", tags = "类别子类")
public class SubclassController extends BaseController {

    private SubclassService service;

    @Autowired
    public void setService(SubclassService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ApiOperation("通过类别id获取类别下所有子类")
    public Object getSubclassById(@RequestParam Integer id) {
        List<Subclass> subclasses = service.getAllById(id);
        if (subclasses != null){
            return subclasses;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/exist/{id}/{name}")
    @ApiOperation("判断子类是否存在")
    public StrResponseData subclassIsExist(@PathVariable Integer id,
                                           @NotBlank @PathVariable String name) {
        if (service.isExist(id, name)) {
            return errorResponse("子类不存在");
        }else {
            return successResponse("子类已存在");
        }
    }

    @PostMapping("/add/{id}/{name}")
    @ApiOperation("新增类别下子类（判断子类是否存在）")
    public StrResponseData addSubclass(@PathVariable Integer id,
                                       @NotBlank @PathVariable String name){
        if (service.addSubclass(id, name)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/search/{id}/{name}")
    @ApiOperation("搜索子类")
    public Object searchSubclass(@PathVariable Integer id,
                                 @NotBlank @PathVariable String name) {
        List<Subclass> subclassList = service.searchSubclass(id, name);
        if (subclassList != null) {
            return subclassList;
        }else {
            return errorResponse("搜索失败");
        }
    }
}
