package com.nebula.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nebula.blogapp.payloads.CommentDto;
import com.nebula.blogapp.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comment")
    public ResponseEntity<CommentDto> createComment(
        @RequestBody CommentDto commentDto,
        @PathVariable Integer postId,
        @PathVariable Integer userId
    ){
        CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(
        @PathVariable Integer commentId
    ){
        CommentDto deletedCommentDto = this.commentService.deleteComment(commentId);
        return new ResponseEntity<CommentDto>(deletedCommentDto, HttpStatus.OK);
    }
}
