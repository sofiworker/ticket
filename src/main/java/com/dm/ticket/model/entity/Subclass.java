package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @description 类别子类
 */

@Data
@Entity
public class Subclass {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer categoryId;

    @Column(nullable = false)
    private String name;
}
