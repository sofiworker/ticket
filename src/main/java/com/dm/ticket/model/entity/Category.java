package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description 演出类别
 */
@Data
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = -7236024574529904162L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer id = 0;

    @Column(nullable = false)
    private String name;

    @Column
    private String pic;
}
