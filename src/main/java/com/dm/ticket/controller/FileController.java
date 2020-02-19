package com.dm.ticket.controller;

import cn.hutool.core.util.StrUtil;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@Api(produces = "application/json", tags = "图片、文件")
public class FileController extends BaseController {

    private FileService service;

    @Autowired
    public void setService(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    @ApiOperation("文件、图片上传")
    public StrResponseData uploadFile(@RequestPart MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return errorResponse("文件为空");
        }
        String url = service.saveFile(file);
        if (!StrUtil.isBlank(url)) {
            return successResponse(url);
        }else {
            return errorResponse("保存失败");
        }
    }
}
