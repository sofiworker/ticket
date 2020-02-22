package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dm.ticket.util.TimeUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @description 演出
 */

@Data
@Entity
public class Perform {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 演出海报封面
     */
    @Column(nullable = false)
    @NotBlank
    private String cover;

    @Column(nullable = false)
    @NotBlank
    private String title;

    /**
     * 演出内容描述
     */
    @Lob
    @Column(nullable = false)
    @NotBlank
    private String content;

    /**
     * 购票须知
     */
    @Lob
    @Column(nullable = false)
    @NotBlank
    private String ticketNotice;

    /**
     * 观演须知
     */
    @Lob
    @Column(nullable = false)
    @NotBlank
    private String viewNotice;

    /**
     * 类别id
     */
    @Column(nullable = false)
    private Integer categoryId;

    /**
     * 类别子类id
     */
    @Column(nullable = false)
    private Integer subclassId;

    /**
     * 演出艺人id
     */
    @Column
    private Integer artistId;

    /**
     * 城市id
     */
    @Column(nullable = false)
    private Long cityId;

    /**
     * 具体地址id
     */
    @Column(nullable = false)
    private Long locationId;

    @Column(nullable = false)
    private Timestamp createTime = TimeUtil.nowTime();
}
