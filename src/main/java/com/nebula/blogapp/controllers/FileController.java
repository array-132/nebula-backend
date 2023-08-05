package com.nebula.blogapp.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nebula.blogapp.payloads.FileResponse;
import com.nebula.blogapp.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image){
        String fileName = null;
        try{
        fileName = this.fileService.uploadImage(path, image);
        }catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<FileResponse>(new FileResponse(null,"Image is not uploaded due to error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileResponse>(new FileResponse(fileName,"Image is successfully uploaded!!!"),HttpStatus.OK);
    }

    // method to serve files
    @GetMapping(value = "/profiles/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException{

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
