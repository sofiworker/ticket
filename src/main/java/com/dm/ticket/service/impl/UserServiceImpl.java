package com.dm.ticket.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.UserMapper;
import com.dm.ticket.model.dto.*;
import com.dm.ticket.model.entity.User;
import com.dm.ticket.service.UserService;
import com.dm.ticket.util.EncryptUtil;
import com.dm.ticket.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean isPhoneNumberRegister(String phoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getPhoneNumber, phoneNumber);
        return userMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public boolean isEmailBind(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getEmail, email);
        return userMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public boolean registerUser(RegisterDto dto) {
        User user = new User();
        String password = EncryptUtil.encryptPassword(dto.getPassword());
        user.setNickName("大麦"+RandomUtil.randomString(5));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(password);
        return userMapper.insert(user) == 1;
    }

    @Override
    public Object login(LoginDto dto) {
        String encryptPassword = EncryptUtil.encryptPassword(dto.getPassword());
        String account = dto.getAccount();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (Validator.isMobile(account)) {
            queryWrapper.lambda().eq(User::getPhoneNumber, account);
        }else if (Validator.isEmail(account)) {
            queryWrapper.lambda().eq(User::getEmail, account);
        }
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }else if (encryptPassword.equals(user.getPassword())) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setToken(JwtUtil.createToken());
            return userDto;
        }else {
            return null;
        }
    }

    @Override
    public boolean bindEmail(String email, Long id) {
        return userMapper.updateEmail(email, id) == 1;
    }

    @Override
    public boolean realVerified(RealInfoDto dto) {
        return userMapper.updateRealInfo(dto) == 1;
    }

    @Override
    public boolean changePhone(Long uid, String phone, String pwd) {
        User user = userMapper.selectById(uid);
        if (EncryptUtil.encryptPassword(pwd).equals(user.getPassword())) {
            user.setPhoneNumber(phone);
            userMapper.updateById(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean findPwd(String phone, String pwd) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone_number", phone);
        User user = userMapper.selectOne(query);
        if (user != null){
            user.setPassword(EncryptUtil.encryptPassword(pwd));
            userMapper.updateById(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean changePwd(Long uid, String oldPwd, String newPwd) {
        User user = userMapper.selectById(uid);
        if (EncryptUtil.encryptPassword(oldPwd).equals(user.getPassword()) && !oldPwd.equals(newPwd)) {
            user.setPassword(EncryptUtil.encryptPassword(newPwd));
            userMapper.updateById(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean changeInfo(UserInfoUpdateDto info) {
        User user = userMapper.selectById(info.getUid());
        user.setNickName(info.getNickName());
        user.setSex(info.getSex());
        user.setBirthday(info.getBirthday());
        return userMapper.updateById(user) == 1;
    }

    @Override
    public UserInfoDto getUserInfo(Long uid) {
        User user = userMapper.selectById(uid);
        UserInfoDto dto = new UserInfoDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
