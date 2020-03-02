package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.SubclassMapper;
import com.dm.ticket.model.entity.Subclass;
import com.dm.ticket.service.SubclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SubclassServiceImpl implements SubclassService {

    private SubclassMapper mapper;

    @Autowired
    public void setMapper(SubclassMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Subclass> getAllById(Integer id) {
        QueryWrapper<Subclass> query = new QueryWrapper<>();
        query.eq("category_id", id);
        return mapper.selectList(query);
    }

    @Override
    public boolean isExist(Integer id, String name) {
        QueryWrapper<Subclass> query = new QueryWrapper<>();
        query.eq("category_id", id).eq("name", name);
        return mapper.selectOne(query) == null;
    }

    @Override
    public boolean addSubclass(Integer id, String name) {
        Subclass subclass = new Subclass();
        subclass.setCategoryId(id);
        subclass.setName(name);
        return mapper.insert(subclass) == 1;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Subclass> searchSubclass(Integer id, String name) {
        QueryWrapper<Subclass> query = new QueryWrapper<>();
        query.eq("category_id", id).like("name", name);
        return mapper.selectList(query);
    }
}
