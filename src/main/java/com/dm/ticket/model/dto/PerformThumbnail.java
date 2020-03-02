package com.dm.ticket.model.dto;

import com.dm.ticket.model.entity.Artist;
import com.dm.ticket.model.entity.Category;
import com.dm.ticket.model.entity.City;
import com.dm.ticket.model.entity.Location;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class PerformThumbnail {

    private Long id;
    private String cover;
    private String title;
    private Integer pageViews;
    private Artist artist;
    private City city;
    private Location location;
    private Timestamp time;
    private BigDecimal money;
    private Category category;
    private Timestamp createTime;
}
