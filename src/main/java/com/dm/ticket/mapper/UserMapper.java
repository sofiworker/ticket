package com.dm.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.ticket.model.dto.RealInfoDto;
import com.dm.ticket.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set email = #{email} where id = #{id}")
    int updateEmail(String email, Long id);

    @Update("update user set actual_name = #{actualName}, id_number = #{idNumber} where id = #{id}")
    int updateRealInfo(RealInfoDto dto);
}
