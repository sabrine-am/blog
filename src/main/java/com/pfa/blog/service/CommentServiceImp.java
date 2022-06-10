package com.pfa.blog.service;

import com.pfa.blog.entity.Comment;
import com.pfa.blog.entity.Post;
import com.pfa.blog.exception.ResourceNotFoundException;
import com.pfa.blog.payloads.CommentDto;
import com.pfa.blog.repository.CommentRepository;
import com.pfa.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedCam = commentRepository.save(comment);
        return modelMapper.map(savedCam, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment>  comments= commentRepository.findAll();
        List<CommentDto> commentDtos= comments.stream()
                .map(comment -> this.modelMapper.map(comment,CommentDto.class))
                .collect(Collectors.toList());
        return commentDtos;
    }
}
