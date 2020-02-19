package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.entity.Artist;
import com.dm.ticket.service.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequestMapping("/artist")
@Api(produces = "application/json", tags = "艺术家/团队")
public class ArtistController extends BaseController {

    private ArtistService service;

    @Autowired
    public void setService(ArtistService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @ApiOperation("获取所有艺术家/团体")
    public Object getAllArtist() {
        List<Artist> artistList = service.getAllArtist();
        if (artistList != null) {
            return artistList;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/exist/{name}")
    @ApiOperation("查询是否存在")
    public StrResponseData isExist(@NotBlank @PathVariable String name){
        if (service.isExist(name)) {
            return successResponse("已存在");
        }else {
            return errorResponse("未存在");
        }
    }

    @PostMapping("/add/{name}")
    @ApiOperation("新增艺术家/团体（先判断是否存在）")
    public StrResponseData addNewArtist(@NotBlank @PathVariable String name) {
        if (service.addNewArtist(name)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/search/{name}")
    @ApiOperation("搜索艺术家/团体")
    public Object searchArtist(@NotBlank @PathVariable String name){
        List<Artist> artists = service.searchArtist(name);
        if (artists != null) {
            return artists;
        }else {
            return errorResponse("搜索失败");
        }
    }
}
