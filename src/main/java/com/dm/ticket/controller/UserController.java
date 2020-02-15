package com.dm.ticket.controller;

import cn.hutool.core.lang.Validator;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.EmailDto;
import com.dm.ticket.model.dto.LoginDto;
import com.dm.ticket.model.dto.RealInfoDto;
import com.dm.ticket.model.dto.RegisterDto;
import com.dm.ticket.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(produces = "{application/json}", tags = {"用户控制器"})
public class UserController extends BaseController {

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping("/register/phone")
    @ApiOperation("判断手机号是否注册")
    public StrResponseData isPhoneNumberRegister(@RequestParam String phoneNumber) {
        if (!Validator.isMobile(phoneNumber)) {
            return errorResponse("请输入正确格式的手机号");
        }
        if (service.isPhoneNumberRegister(phoneNumber)) {
            return successResponse("手机号已注册");
        }else {
            return successResponse("手机号未注册");
        }
    }

    @GetMapping("/register/email")
    @ApiOperation("判断邮箱是否绑定")
    public StrResponseData isEmailRegister(@RequestParam String email) {
        if (!Validator.isEmail(email)) {
            return errorResponse("请输入正确格式的邮箱");
        }
        if (service.isEmailBind(email)) {
            return successResponse("邮箱已绑定");
        }else {
            return successResponse("邮箱未绑定");
        }
    }

    @PostMapping("/register")
    @ApiOperation("用户注册（请先判断手机号是否已注册）")
    public StrResponseData registerUser(@Valid @RequestBody RegisterDto dto) {
        if (!Validator.isMobile(dto.getPhoneNumber())) {
            return errorResponse("请输入正确格式的手机号");
        }
        if (service.registerUser(dto)) {
            return successResponse("注册成功");
        }else {
            return errorResponse("注册失败");
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录（请先判断手机号是否注册及邮箱是否绑定）")
    public Object login(@Valid @RequestBody LoginDto dto) {
        if (!Validator.isMobile(dto.getAccount()) && !Validator.isEmail(dto.getAccount())) {
            return errorResponse("请输入正确的手机号/邮箱");
        }
        Object o = service.login(dto);
        return o == null ? errorResponse("登录失败") : o;
    }

    @PostMapping("/bind/email")
    @ApiOperation("用户绑定/更新邮箱")
    public StrResponseData bindEmail(@Valid @RequestBody EmailDto dto){
        if (service.bindEmail(dto.getEmail(), dto.getId())) {
            return successResponse("绑定/更新成功");
        }else {
            return errorResponse("绑定/更新失败");
        }
    }

    @PostMapping("/bind/real")
    @ApiOperation("绑定真实信息")
    public StrResponseData bindRealInfo(@Valid @RequestBody RealInfoDto dto){
        if (!Validator.isCitizenId(dto.getIdNumber())) {
            return errorResponse("请输入正确格式的身份证号");
        } else if (service.realVerified(dto)) {
            return successResponse("认证成功");
        }else {
            return errorResponse("认证失败");
        }
    }
}
