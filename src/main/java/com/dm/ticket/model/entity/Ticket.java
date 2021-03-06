package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 票档
 */

@Data
@Entity
public class Ticket implements Serializable {

    private static final long serialVersionUID = 8828985424830874856L;

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private Long performId;

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
