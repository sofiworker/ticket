package com.dm.ticket.controller;

import cn.hutool.core.lang.Validator;
import com.dm.ticket.model.StrResponseData;
import com.dm.ticket.model.dto.*;
import com.dm.ticket.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(produces = "{application/json}", tags = {"用户"})
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
    @ApiOperation("实名认证")
    public StrResponseData bindRealInfo(@Valid @RequestBody RealInfoDto dto){
        if (!Validator.isCitizenId(dto.getIdNumber())) {
            return errorResponse("请输入正确格式的身份证号");
        } else if (service.realVerified(dto)) {
            return successResponse("认证成功");
        }else {
            return errorResponse("认证失败");
        }
    }

    @PostMapping("/phone")
    @ApiOperation("更改手机（先判断手机号是否已注册）")
    public StrResponseData changePhone(@RequestParam Long uid,
                                       @Size(max = 11, min = 11) @RequestParam String phone,
                                       @Size(max = 20, min = 6) @RequestParam String pwd){
        if (!Validator.isMobile(phone)) {
            return errorResponse("请输入正确格式的手机号");
        }else if (service.changePhone(uid, phone, pwd)) {
            return successResponse("更改成功");
        } else {
            return errorResponse("更改失败");
        }
    }

    @PostMapping("/pwd/find")
    @ApiOperation("找回密码")
    public StrResponseData findPassword(@Size(max = 11, min = 11) @RequestParam String phone,
                                          @Size(max = 20, min = 6) @RequestParam String pwd){
        if (service.findPwd(phone, pwd)) {
            return successResponse("成功");
        } else {
            return errorResponse("失败");
        }
    }

    @PostMapping("/pwd/modify")
    @ApiOperation("修改密码（登录后）")
    public StrResponseData changePassword(@RequestParam Long uid,
                                          @Size(max = 20, min = 6) @RequestParam String oldPwd,
                                          @Size(max = 20, min = 6) @RequestParam String newPwd){
        if (service.changePwd(uid, oldPwd, newPwd)) {
            return successResponse("修改成功");
        } else {
            return errorResponse("修改失败");
        }
    }

    @PostMapping("/info")
    @ApiOperation("修改用户信息")
    public StrResponseData modifyInfo(@Valid @RequestBody UserInfoUpdateDto info){
        if (service.changeInfo(info)) {
            return successResponse("修改成功");
        }else {
            return errorResponse("修改失败");
        }
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Object Info(Long uid){
        UserInfoDto userInfo = service.getUserInfo(uid);
        if (userInfo != null) {
            return userInfo;
        }else {
            return errorResponse("获取失败");
        }
    }
}
