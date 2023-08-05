package com.nebula.blogapp.controllers;

import java.io.IOException;
import java.io.InputStream;
// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nebula.blogapp.config.AppConstants;
import com.nebula.blogapp.payloads.ApiResponse;
import com.nebula.blogapp.payloads.PostDto;
import com.nebula.blogapp.payloads.PostResponse;
import com.nebula.blogapp.services.FileService;
import com.nebula.blogapp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    // create
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(
        @Valid @RequestBody PostDto postDto,
        @PathVariable Integer userId,
        @PathVariable Integer categoryId){

            PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
            return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
    }

    // get posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
       @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
       @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
){
        PostResponse postResponse = this.postService.getPostsByUser(userId,pageNumber,pageSize);
        return ResponseEntity.ok(postResponse);        
    }

    // get posts by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
        @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
    ){
        PostResponse postResponse = this.postService.getPostsByCategory(categoryId,pageNumber,pageSize);
        return ResponseEntity.ok(postResponse);        
    }

    // get post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    // get all posts
    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    //search posts by keyword
    @GetMapping("/{keyword}/")
    public ResponseEntity<PostResponse> getPostsByKeyword(@PathVariable String keyword,
        @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
){
        PostResponse searchResults = this.postService.searchPosts(keyword,pageNumber,pageSize);
        if(searchResults.getContent().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(searchResults);
    }

    // update a post with given id
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedContent = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedContent);
    }
    
    // delete a post with given id
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post with id "+postId+" deleted successfully",true),HttpStatus.OK); 
    }

    // post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable("postId") Integer postId
    ) throws IOException{
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatePost);
    }

     // method to serve files
     @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
     public void downloadImage(
         @PathVariable("imageName") String imageName,
         HttpServletResponse response
     ) throws IOException{
 
         InputStream resource = this.fileService.getResource(path, imageName);
         response.setContentType(MediaType.IMAGE_JPEG_VALUE);
         StreamUtils.copy(resource,response.getOutputStream());
     }
}