package com.nebula.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebula.blogapp.entities.Comment;
import com.nebula.blogapp.entities.Post;
import com.nebula.blogapp.entities.User;
import com.nebula.blogapp.exceptions.ResourceNotFoundException;
import com.nebula.blogapp.payloads.CommentDto;
import com.nebula.blogapp.repositories.CommentRepository;
import com.nebula.blogapp.repositories.PostRepository;
import com.nebula.blogapp.repositories.UserRepository;
import com.nebula.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment updatedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public CommentDto deleteComment(Integer commentId) {
        Comment c = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
        this.commentRepository.delete(c);
        return this.modelMapper.map(c, CommentDto.class);
    }
    
}