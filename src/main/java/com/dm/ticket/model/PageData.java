package com.dm.ticket.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *分页
 */
@Data
@Accessors(chain = true)
public class PageData<T> {

    private Long curPage;
    private Long totalPage;
    private T data;
}
