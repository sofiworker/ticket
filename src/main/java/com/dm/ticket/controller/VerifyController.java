package com.dm.ticket.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.dm.ticket.model.StrResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/verify")
@Api(produces = "application/json", tags = "验证码")
public class VerifyController extends BaseController {

    @GetMapping("/code")
    @ApiOperation("获取验证码")
    public void getVerificationCode(@RequestParam(defaultValue = "200") int width,
                                    @RequestParam(defaultValue = "100") int height,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(width, height, 4, 4);
        response.setContentType("image/jpeg");
        request.getSession().setAttribute("captcha", captcha);
        request.getSession().setMaxInactiveInterval(60);
        try {
            response.getOutputStream().write(captcha.getImageBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/{code}")
    @ApiOperation("验证验证码")
    public StrResponseData verifyCode(HttpServletRequest request,
                                      @PathVariable String code){
        if (request.getSession(false) == null) {
            return errorResponse("验证码已过期");
        }else {
            ShearCaptcha captcha = (ShearCaptcha) request.getSession().getAttribute("captcha");
            if (captcha.verify(code)) {
                return successResponse("验证成功");
            }else {
                return errorResponse("验证失败");
            }
        }
    }
}
