package com.dm.ticket.controller;

import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.TimeDto;
import com.dm.ticket.model.entity.Time;
import com.dm.ticket.service.TimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/time")
@Api(produces = "application/json", tags = "演出时间（在演出新增成功后调用）")
public class TimeController extends BaseController {

    private TimeService timeService;

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/add")
    @ApiOperation("新增演出时间")
    public StrResponseData addNewTime(@NotEmpty @RequestBody List<TimeDto> timeList) {
        if (timeService.addNewTime(timeList)) {
            return successResponse("新增成功");
        }else {
            return errorResponse("新增失败");
        }
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("通过performId获取演出时间")
    public Object getTime(@PathVariable Long id) {
        List<Time> times = timeService.getTime(id);
        if (times != null) {
            return times;
        }else {
            return errorResponse("获取失败");
        }
    }

    @PostMapping("/delete/{performId}")
    @ApiOperation("通过performId删除times")
    public StrResponseData deleteTimes(@PathVariable Long performId) {
        if (timeService.deleteTimes(performId)) {
            return successResponse("删除成功");
        }else {
            return errorResponse("删除失败");
        }
    }
}
