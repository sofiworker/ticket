package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * @description 用户收货地址
 */
@Data
@Entity
public class Ship {

    @JsonIgnore
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private Long uid;

    /**
     * 收货人姓名
     */
    @NotBlank
    @Column(nullable = false)
    private String name;

    /**
     * 收货人手机号
     */
    @NotBlank
    @Column(nullable = false)
    private String phone;

    /**
     * 收货地址
     */
    @NotBlank
    @Column(nullable = false)
    private String location;
}
