package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @description 演出类别
 */
@Data
@Entity
public class Category {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String pic;
}
