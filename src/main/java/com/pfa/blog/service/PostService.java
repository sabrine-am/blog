package com.pfa.blog.service;

import com.pfa.blog.entity.Post;
import com.pfa.blog.payloads.PostDto;
import com.pfa.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    PostDto getPostById(Integer postId);
    PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy);
    void deletePost(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
}
