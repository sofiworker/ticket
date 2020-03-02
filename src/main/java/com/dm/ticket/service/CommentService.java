package com.dm.ticket.service;


import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.CommentDto;
import com.dm.ticket.model.entity.Comment;

import java.util.List;

public interface CommentService {

    boolean addNewComment(Comment comment);

    PageData<List<CommentDto>> getComments(Long id, Long pageNum);

    boolean ableComment(Long performId, Long userId);
}
