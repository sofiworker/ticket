package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.ArtistMapper;
import com.dm.ticket.model.entity.Artist;
import com.dm.ticket.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArtistServiceImpl implements ArtistService {

    private ArtistMapper artistMapper;

    @Autowired
    public void setArtistMapper(ArtistMapper artistMapper) {
        this.artistMapper = artistMapper;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Artist> getAllArtist() {
        return artistMapper.selectList(null);
    }

    @Override
    public boolean isExist(String name) {
        QueryWrapper<Artist> query = new QueryWrapper<>();
        query.eq("name", name);
        return artistMapper.selectOne(query) != null;
    }

    @Override
    public boolean addNewArtist(String name) {
        Artist artist = new Artist();
        artist.setName(name);
        return artistMapper.insert(artist) == 1;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public List<Artist> searchArtist(String name) {
        QueryWrapper<Artist> query = new QueryWrapper<>();
        query.like("name", name);
        return artistMapper.selectList(query);
    }
}
