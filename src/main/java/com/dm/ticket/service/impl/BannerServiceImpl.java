package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dm.ticket.mapper.BannerMapper;
import com.dm.ticket.model.entity.Banner;
import com.dm.ticket.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BannerServiceImpl implements BannerService {

    private BannerMapper mapper;

    @Autowired
    public void setMapper(BannerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addBanner(Banner banner) {
        return mapper.insert(banner) == 1;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    @Transactional(readOnly = true)
    public List<Banner> getBannerList() {
        QueryWrapper<Banner> query = new QueryWrapper<>();
        query.orderByDesc("create_time");
        List<Banner> banners = mapper.selectList(query);
        List<Banner> bannerList = new ArrayList<>();
        if (banners.size() < 3) {
            bannerList.addAll(banners);
        }else {
            for (int i = 0; i < 3; i++) {
                bannerList.add(banners.get(i));
            }
        }
        return bannerList;
    }
}
