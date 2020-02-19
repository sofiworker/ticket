package com.dm.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.ArtistMapper;
import com.dm.ticket.model.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private ArtistMapper artistMapper;

    @Autowired
    public void setArtistMapper(ArtistMapper artistMapper) {
        this.artistMapper = artistMapper;
    }

    @Override
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
    public List<Artist> searchArtist(String name) {
        QueryWrapper<Artist> query = new QueryWrapper<>();
        query.like("name", name);
        return artistMapper.selectList(query);
    }
}
