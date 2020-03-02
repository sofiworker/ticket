package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @description 演出城市
 */
@Data
@Entity
public class City implements Serializable {

    private static final long serialVersionUID = 5010117525696409604L;
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private String name;
}
