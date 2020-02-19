package com.dm.ticket.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PerformDto {

    @NotBlank
    private String cover;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String ticketNotice;

    @NotBlank
    private String viewNotice;

    private Integer categoryId;

    private Integer subclassId;

    private Integer artistId;

    private Long cityId;

    private Long locationId;
}
