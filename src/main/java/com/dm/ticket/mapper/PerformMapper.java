package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.model.entity.Artist;
import com.dm.ticket.model.entity.Perform;
import com.dm.ticket.model.entity.Time;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PerformMapper extends BaseMapper<Perform> {

    @Select("<script>" + "select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id, page_views from perform <where><foreach collection='conditions.keys' item='key' separator=' and '>" +
            "${key} = #{conditions[${key}]}</foreach> <if test='!times.isEmpty'> and id in <foreach collection='times' item='item' open='(' separator=',' close=')'>#{item.performId}</foreach></if></where>" +
            "order by <choose><when test='orderType == 0'>page_views desc</when>" +
            "<when test='orderType == 1'>create_time asc</when></choose></script>")
    IPage<Perform> getPerformData(Page<Perform> page, Integer orderType, Map<String, Long> conditions, List<Time> times);

    @Select("<script>" + "select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id, page_views from perform <where><foreach collection='conditions.keys' item='key' separator=' and '>" +
            "${key} = #{conditions[${key}]}</foreach></where>" +
            "order by <choose><when test='orderType == 0'>page_views desc</when>" +
            "<when test='orderType == 1'>create_time asc</when></choose></script>")
    IPage<Perform> selectThumbnail(Page<Perform> page, Integer orderType, Map<String, Long> conditions);

    @Select("<script>" + "select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id, page_views from perform <where><foreach collection='conditions.keys' item='key' separator=' and '>" +
            "${key} = #{conditions[${key}]}</foreach></where>" +
            "order by <choose><when test='orderType == 0'>page_views desc</when>" +
            "<when test='orderType == 1'>create_time asc</when></choose></script>")
    IPage<Perform> getOverViewPerform(Page<Perform> page, Integer orderType, Map<String, Long> conditions);

    @Select("<script>" + "select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id, page_views from perform where (title like CONCAT('%',#{str},'%')" +
            "<if test='artists.isEmpty'>)</if>" +
            "<if test='!artists.isEmpty'> or artist_id in<foreach collection='artists' item='item' index='index' open='(' separator=',' close=')'> #{item.id}" +
            "</foreach>)</if>" + "<if test='!conditions.isEmpty'> and <foreach collection='conditions.keys' item='key' separator=' and '>" +
            "${key} = #{conditions[${key}]}</foreach></if>order by <choose><when test='orderType == 0'>page_views desc</when>" +
            "<when test='orderType == 1'>create_time asc</when></choose></script>")
    IPage<Perform> searchPerform(Page<Perform> page, String str, List<Artist> artists, Integer orderType, Map<String, Object> conditions);

    @Update("update perform set page_views = page_views + 1 where id = #{performId}")
    int updateViewPages(Long performId);
}
