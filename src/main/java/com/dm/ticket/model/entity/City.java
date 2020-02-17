package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @description 演出城市
 */
@Data
@Entity
public class City {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private String name;
}
