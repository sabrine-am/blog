package com.pfa.blog.service;

import com.pfa.blog.entity.Category;
import com.pfa.blog.entity.Post;
import com.pfa.blog.entity.User;
import com.pfa.blog.exception.ResourceNotFoundException;
import com.pfa.blog.payloads.PostDto;
import com.pfa.blog.payloads.PostResponse;
import com.pfa.blog.repository.CategoryRepository;
import com.pfa.blog.repository.PostRepository;
import com.pfa.blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        Post post=modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setCreatedAt(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost=postRepository.save(post);
        return modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCreatedAt(postDto.getCreatedAt());
        Post updatedPost=postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageSize, Integer pageNumber,String sortBy) {
        Pageable p = PageRequest.of(pageSize,pageNumber, Sort.by(sortBy));
        Page<Post> postPage= postRepository.findAll(p);
        List<Post>  allPosts= postPage.getContent();
        List<PostDto> postDtos= allPosts.stream()
                .map(post -> this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        List<Post> posts = postRepository.findByCategory(category);
        List<PostDto> postDtos = posts.stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        List<Post> posts = postRepository.findByUser(user);
        List<PostDto> postDtos = posts.stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
}
