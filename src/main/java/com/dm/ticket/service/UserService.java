package com.dm.ticket.service;


import com.dm.ticket.model.dto.*;

import java.util.Map;

public interface UserService {

    boolean isPhoneNumberRegister(String phoneNumber);

    boolean registerUser(RegisterDto dto);

    Object login(LoginDto dto);

    boolean isEmailBind(String email);

    boolean bindEmail(String email, Long id);

    boolean realVerified(RealInfoDto dto);

    boolean changePhone(Long uid, String phone, String pwd);

    boolean findPwd(String phone, String pwd);

    boolean changePwd(Long uid, String oldPwd, String newPwd);

    boolean changeInfo(UserInfoUpdateDto info);

    UserInfoDto getUserInfo(Long uid);
}
