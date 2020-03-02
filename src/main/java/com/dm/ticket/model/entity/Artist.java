package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description 演出艺人/团队
 */

@Data
@Entity
public class Artist implements Serializable {

    private static final long serialVersionUID = 8574917887725881453L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
