package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @description 演出时间
 */
@Data
@Entity
public class Time {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private Long performId;

    @Column(nullable = false)
    private Timestamp time;
}
