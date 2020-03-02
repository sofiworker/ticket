package com.dm.ticket.model.dto;

import com.dm.ticket.model.entity.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class PerformOverview {

    private Long id;

    private String cover;

    private String title;

    private String content;

    private String ticketNotice;

    private String viewNotice;

    private Category category;

    private Subclass subclass;

    private Artist artist;

    private City city;

    private Location location;

    private Integer pageViews;

    private Timestamp createTime;
}
