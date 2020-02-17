package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @description 票档
 */

@Data
@Entity
public class Ticket {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 价钱
     */
    @Column(nullable = false)
    private BigDecimal money;

    /**
     * 票总数
     */
    @Column(nullable = false)
    private Integer count;

    /**
     * 描述
     */
    @Column
    private String description;
}
