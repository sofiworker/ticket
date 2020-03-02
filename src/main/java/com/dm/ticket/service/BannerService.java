package com.dm.ticket.service;


import com.dm.ticket.model.entity.Banner;

import java.util.List;

public interface BannerService {

    boolean addBanner(Banner banner);

    List<Banner> getBannerList();
}
