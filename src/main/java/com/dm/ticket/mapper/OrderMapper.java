package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.model.entity.OrderForPerform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderForPerform> {

    @Select("select id, uid, ship_id, perform_id, count, money, state, create_time, ticket_id from order_for_perform where uid = #{userId}")
    IPage<OrderForPerform> getUserOrders(Page<OrderForPerform> page, Long userId);

    @Update("update order_for_perform set state = #{status} where id = #{orderId}")
    void updateOrderStatus(Long orderId, Integer status);
}
