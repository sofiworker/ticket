package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dm.ticket.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
public class Banner implements Serializable {

    private static final long serialVersionUID = 3795595869874926580L;
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private Long performId;

    @Column(nullable = false)
    @NotBlank
    private String cover;

    private String title;

    @Column(nullable = false)
    private Timestamp createTime = TimeUtil.nowTime();
}
