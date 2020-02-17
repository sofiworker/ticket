package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /**
     * 城市id（多对1）
     */
    @Column(nullable = false)
    private Long cityId;

    /**
     * 票档id（1对多）
     */
    @Column(nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    private Timestamp time;
}
