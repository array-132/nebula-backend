package com.nebula.blogapp.services;
import com.nebula.blogapp.payloads.PostDto;
import com.nebula.blogapp.payloads.PostResponse;

public interface PostService {
    
    // create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    // update
    PostDto updatePost(PostDto postDto,Integer postId);
    // delete
    void deletePost(Integer postId);
    // get all posts
    PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
    // get a post by postId
    PostDto getPostById(Integer postId);
    // get all posts by category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);
    // get all posts by user
    PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize);
    // search posts by a keyword
    PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize);
}