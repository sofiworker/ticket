package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
public class Comment {

    @JsonIgnore
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long performId;

    @Lob
    @Column(nullable = false)
    @NotBlank
    private String content;

    @Column(nullable = false)
    private Timestamp createTime;
}
