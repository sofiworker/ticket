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

/**
 * @description 演出
 */

@Data
@Entity
public class Perform {

    @JsonIgnore
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 演出海报封面
     */
    @Lob
    @Column(nullable = false)
    @NotBlank
    private String cover;

    @Column(nullable = false)
    @NotBlank
    private String title;

    /**
     * 演出时间id（1对n）
     */
    @Column(nullable = false)
    private Long timeId;

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
     * 类别id（1对1）
     */
    @Column(nullable = false)
    private Integer categoryId;

    /**
     * 类别子类id（1对1）
     */
    @Column(nullable = false)
    private Integer subclassId;
}
