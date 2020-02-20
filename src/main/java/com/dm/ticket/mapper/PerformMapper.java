package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.entity.Perform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PerformMapper extends BaseMapper<Perform> {

    @Select("select * from perform")
    IPage<Perform> getData(Page<Perform> page);
}
