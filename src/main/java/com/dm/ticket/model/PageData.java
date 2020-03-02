package com.dm.ticket.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *分页
 */
@Data
@Accessors(chain = true)
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = -5711033379447259641L;
    private Long curPage = 0L;
    private Long total = 0L;
    private Long totalPage = 0L;
    private T data;
}
