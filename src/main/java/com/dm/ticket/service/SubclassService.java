package com.dm.ticket.service;


import com.dm.ticket.model.entity.Subclass;

import java.util.List;

public interface SubclassService {

    List<Subclass> getAllById(Integer id);

    boolean isExist(Integer id, String name);

    boolean addSubclass(Integer id, String name);

    List<Subclass> searchSubclass(Integer id, String name);
}
