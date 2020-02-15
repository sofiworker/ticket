package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dm.ticket.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.sql.Timestamp;

/**
 * @description 用户表
 */
@Data
@Entity
public class User {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(length = 18, nullable = false)
    private String nickName;

    /**
     * 真实姓名
     */
    @Column
    private String actualName;

    @Column(nullable = false)
    private String password;

    /**
     * 头像图片地址
     */
    @Column(nullable = false)
    private String avatar = "https://upload.jianshu.io/users/upload_avatars/11708026/d3d2b4f9-04bd-4106-b2a0-2cb6665ea9cb.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240";

    /**
     * true为男，false为女
     */
    @Column(nullable = false)
    private Boolean sex = true;

    @Column(nullable = false)
    private Timestamp birthday = TimeUtil.nowTime();

    @Column(length = 18)
    private String idNumber;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Email
    @Column
    private String email;

    @Column(nullable = false)
    private Timestamp createTime = TimeUtil.nowTime();
}
