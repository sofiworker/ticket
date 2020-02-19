package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

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
    private String cover;

    @Column(nullable = false)
    private String title;

    /**
     * 演出内容描述
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * 购票须知
     */
    @Lob
    @Column(nullable = false)
    private String ticketNotice;

    /**
     * 观演须知
     */
    @Lob
    @Column(nullable = false)
    private String viewNotice;

    /**
     * 类别id（1对1）
     */
    @Column(nullable = false)
    private Integer categoryId;

    /**
     * 类别子类id（1对1）
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
}
