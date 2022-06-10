package com.pfa.blog.service;


import com.pfa.blog.payloads.CommentDto;
import com.pfa.blog.payloads.PostResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
    List<CommentDto> getAllComments();

}
