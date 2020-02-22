package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dm.ticket.util.TimeUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
public class Order {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @Column(nullable = false)
    private Long uid;

    /**
     * 用户地址id
     */
    @Column(nullable = false)
    private Long shipId;

    /**
     * 演出id
     */
    @Column(nullable = false)
    private Long performId;

    /**
     * 下单个数
     */
    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private BigDecimal money;

    /**
     * 订单状态
     * 0：待支付
     * 1：成功
     * 2：失败
     */
    @Column(nullable = false)
    private Integer state;

    /**
     * 创建订单时间
     */
    @Column(nullable = false)
    private Timestamp createTime = TimeUtil.nowTime();
}
