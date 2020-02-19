package com.dm.ticket.service;


import com.dm.ticket.model.dto.CategoryDto;
import com.dm.ticket.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    boolean addNewCategory(CategoryDto dto);

    boolean isExist(String name);

    List<Category> searchCategory(String name);
}
