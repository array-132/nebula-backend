package com.nebula.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nebula.blogapp.entities.Category;
import com.nebula.blogapp.entities.Post;
import com.nebula.blogapp.entities.User;
import com.nebula.blogapp.exceptions.ResourceNotFoundException;
import com.nebula.blogapp.payloads.PostDto;
import com.nebula.blogapp.payloads.PostResponse;
import com.nebula.blogapp.repositories.CategoryRepository;
import com.nebula.blogapp.repositories.PostRepository;
import com.nebula.blogapp.repositories.UserRepository;
import com.nebula.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        
        User user =  this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", "User id", userId));

        Category category =  this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category id", userId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setLastEditDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post addedPost = this.postRepository.save(post);
        return this.modelMapper.map(addedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post "," post id ", postId));    
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if(sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        Pageable pages = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts = this.postRepository.findAll(pages);
        List<Post> allPosts = pagePosts.getContent();
        List<PostDto> postDtos = allPosts.stream().map(p->modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post "," post id ", postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
        Category cat= this.categoryRepository.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category ", " id ", categoryId));
        Pageable pages = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePosts = this.postRepository.findByCategory(cat,pages);
        List<Post> postsOfCategory = pagePosts.getContent();
        List<PostDto> postDtos = postsOfCategory.stream().map(p->modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User ", " id ", userId));
        Pageable pages = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePosts = this.postRepository.findByUser(user,pages);
        List<Post> postsOfUser = pagePosts.getContent();
        List<PostDto> postDtos = postsOfUser.stream().map(p->modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize) {
        Pageable pages = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePosts = this.postRepository.findByContentContaining(keyword,pages);
        List<Post> searchedPosts = pagePosts.getContent();
        List<PostDto> postDtos = searchedPosts.stream().map(p->modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post "," post id ",postId));
        post.setTitle(postDto.getTitle());
        post.setLastEditDate(new Date());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(modelMapper.map(postDto.getCategory(),Category.class));
        post.setUser(modelMapper.map(postDto.getUser(),User.class));
        this.postRepository.save(post);
        return modelMapper.map(post,PostDto.class);
    }
    
}
