package com.dm.ticket.model.dto;

import com.dm.ticket.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class CommentDto {
    private Long id;
    private User user;
    private String content;
    private Timestamp createTime;
}
