package com.dm.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dm.ticket.mapper.CommentMapper;
import com.dm.ticket.mapper.OrderMapper;
import com.dm.ticket.mapper.UserMapper;
import com.dm.ticket.model.PageData;
import com.dm.ticket.model.dto.CommentDto;
import com.dm.ticket.model.entity.Comment;
import com.dm.ticket.model.entity.OrderForPerform;
import com.dm.ticket.model.entity.User;
import com.dm.ticket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    private CommentMapper commentMapper;
    private UserMapper userMapper;
    private OrderMapper orderMapper;

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public boolean addNewComment(Comment comment) {
        return commentMapper.insert(comment) == 1;
    }

    @Override
    @Cacheable(value = "default", keyGenerator = "cacheKeyGenerator")
    public PageData<List<CommentDto>> getComments(Long id, Long pageNum) {
        Page<Comment> page = new Page<>(pageNum, 30);
        IPage<Comment> comments = commentMapper.getComments(page);
        PageData<List<CommentDto>> pageData = new PageData<>();
        List<Comment> records = comments.getRecords();
        List<CommentDto> dtoList = new ArrayList<>();
        for (Comment record : records) {
            CommentDto dto = new CommentDto();
            User user = userMapper.selectById(record.getUserId());
            dto.setId(record.getId()).setUser(user).setContent(record.getContent())
                    .setCreateTime(record.getCreateTime());
            dtoList.add(dto);
        }
        return pageData.setCurPage(comments.getCurrent()).setTotal(comments.getTotal())
                .setData(dtoList).setTotalPage(comments.getTotal());
    }

    @Override
    public boolean ableComment(Long performId, Long userId) {
        QueryWrapper<OrderForPerform> query = new QueryWrapper<>();
        query.eq("perform_id", performId).eq("uid", userId);
        List<OrderForPerform> orders = orderMapper.selectList(query);
        return orders != null && !orders.isEmpty();
    }
}
