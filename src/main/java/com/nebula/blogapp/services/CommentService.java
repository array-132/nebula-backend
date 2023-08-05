package com.nebula.blogapp.services;

import com.nebula.blogapp.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
    CommentDto deleteComment(Integer commentId);
}
