package com.pfa.blog.controller;

import com.pfa.blog.config.AppConstants;
import com.pfa.blog.payloads.ApiResponse;
import com.pfa.blog.payloads.PostDto;
import com.pfa.blog.payloads.PostResponse;
import com.pfa.blog.service.FileService;
import com.pfa.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/blog/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("%{project.image}")
    private String path;

    @PostMapping("/create/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto postDto
    , @PathVariable("userId") Integer uId,@PathVariable("categoryId") Integer cId){
        PostDto newPost = postService.createPost(postDto,uId,cId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> update(@Valid @RequestBody PostDto postDto, @PathVariable("postId") Integer postId){
        PostDto updatedPost=postService.updatePost(postDto,postId);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer uId){
        List<PostDto> posts = postService.getPostsByUser(uId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer cId){
        List<PostDto> posts = postService.getPostsByCategory(cId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("postId") Integer pId){
        postService.deletePost(pId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value ="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value ="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value ="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy){
        PostResponse postResponse = postService.getAllPosts(pageNumber,pageSize,sortBy);
        return  new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/oneById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer pId){
        return  ResponseEntity.ok(postService.getPostById(pId));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> search(@PathVariable("keyword") String keyword){
        return  ResponseEntity.ok(postService.searchPosts(keyword));
    }


    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                   @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }


    //method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
}
