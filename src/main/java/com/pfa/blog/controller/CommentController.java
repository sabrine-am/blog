package com.pfa.blog.controller;

import com.pfa.blog.payloads.ApiResponse;
import com.pfa.blog.payloads.CategoryDto;
import com.pfa.blog.payloads.CommentDto;
import com.pfa.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blog/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer pId){
        CommentDto createdCam=commentService.createComment(commentDto,pId);
        return new ResponseEntity<>(createdCam, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("commentId") Integer camId){
        commentService.deleteComment(camId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentDto>> getAll(){
        return  ResponseEntity.ok(commentService.getAllComments());
    }

}
