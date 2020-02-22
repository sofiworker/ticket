package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.entity.Artist;
import com.dm.ticket.model.entity.Perform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PerformMapper extends BaseMapper<Perform> {

    @Select("select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id from perform")
    IPage<Perform> getPerformData(Page<Perform> page);

    @Select("<script>" + "select id, cover, title, content, ticket_notice, " +
            "view_notice, category_id, subclass_id, artist_id, city_id, " +
            "location_id from perform where title like CONCAT('%',#{str},'%')" +
            " or artist_id in" +
            "<foreach collection='artists' item='item' index='index' open='(' separator=',' close=')'> #{item.id}" +
            "</foreach></script>")
    IPage<Perform> searchPerform(Page<Perform> page, String str, List<Artist> artists);
}
