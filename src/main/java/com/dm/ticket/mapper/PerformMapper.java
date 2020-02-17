package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.entity.Perform;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PerformMapper extends BaseMapper<Perform> {
}
