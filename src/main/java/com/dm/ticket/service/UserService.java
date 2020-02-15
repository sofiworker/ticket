package com.dm.ticket.service;


import com.dm.ticket.model.dto.LoginDto;
import com.dm.ticket.model.dto.RealInfoDto;
import com.dm.ticket.model.dto.RegisterDto;

public interface UserService {

    boolean isPhoneNumberRegister(String phoneNumber);

    boolean registerUser(RegisterDto dto);

    Object login(LoginDto dto);

    boolean isEmailBind(String email);

    boolean bindEmail(String email, Long id);

    boolean realVerified(RealInfoDto dto);
}
