package com.dm.ticket.service;


import com.dm.ticket.model.entity.Artist;

import java.util.List;

public interface ArtistService {

    List<Artist> getAllArtist();

    boolean isExist(String name);

    boolean addNewArtist(String name);

    List<Artist> searchArtist(String name);
}
