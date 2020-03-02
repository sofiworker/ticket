package com.dm.ticket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dm.ticket.util.TimeUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
public class OrderForPerform implements Serializable {

    private static final long serialVersionUID = -2838938274155138563L;

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
     * 票务id
     */
    @Column(nullable = false)
    private Long ticketId;

    /**
     * 下单个数
     */
    @Column(nullable = false)
    private Integer count;

    /**总金额
     */
    @Column(nullable = false)
    private BigDecimal money;

    /**
     * 订单状态
     * 0：待支付
     * 1：成功
     * 2：取消订单
     * 3：退款成功
     * 4：退款异常
     */
    @Column(nullable = false)
    private Integer state = 0;

    /**
     * 创建订单时间
     */
    @Column(nullable = false)
    private Timestamp createTime = TimeUtil.nowTime();
}
