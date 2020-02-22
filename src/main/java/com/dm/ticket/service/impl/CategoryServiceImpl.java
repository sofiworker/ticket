package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.CategoryMapper;
import com.dm.ticket.model.dto.CategoryDto;
import com.dm.ticket.model.entity.Category;
import com.dm.ticket.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper mapper;

    @Autowired
    public void setMapper(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Category> getAllCategory() {
        return mapper.selectList(null);
    }

    @Override
    public boolean addNewCategory(CategoryDto dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        return mapper.insert(category) == 1;
    }

    @Override
    public boolean isExist(String name) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.eq("name", name);
        return mapper.selectOne(query) != null;
    }

    @Override
    public List<Category> searchCategory(String name) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.like("name", name);
        return mapper.selectList(query);
    }
}
