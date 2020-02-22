package com.dm.ticket.controller;

import com.dm.ticket.model.PageData;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.CommentDto;
import com.dm.ticket.model.entity.Comment;
import com.dm.ticket.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Api(produces = "application/json", tags = "演出评论")
public class CommentController extends BaseController {

    private CommentService service;

    @Autowired
    public void setService(CommentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    @ApiOperation("新增评论")
    public StrResponseData addNewComment(@RequestBody Comment comment) {
        if (service.addNewComment(comment)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/{performId}/{pageNum}")
    @ApiOperation("通过演出id获取评论（含分页，每页30条）")
    public Object getComments(@PathVariable Long performId, @PathVariable Long pageNum) {
        PageData<List<CommentDto>> pageData = service.getComments(performId, pageNum);
        if (pageData != null && pageData.getData() != null) {
            return pageData;
        }else {
            return errorResponse("获取失败");
        }
    }
}
