package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @description 城市地址
 */

@Data
@Entity
public class Location {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long cityId;

    @Column(nullable = false)
    private String detail;
}
