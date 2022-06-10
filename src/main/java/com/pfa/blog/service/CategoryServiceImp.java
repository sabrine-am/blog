package com.pfa.blog.service;

import com.pfa.blog.entity.Category;
import com.pfa.blog.entity.User;
import com.pfa.blog.exception.ResourceNotFoundException;
import com.pfa.blog.payloads.CategoryDto;
import com.pfa.blog.payloads.UserDto;
import com.pfa.blog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = modelMapper.map(categoryDto,Category.class);
        Category savedCat = categoryRepository.save(cat);
        return modelMapper.map(savedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = categoryRepository.save(cat);
        return modelMapper.map(updatedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category cat=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        return modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category>  categories= categoryRepository.findAll();
        List<CategoryDto> categoryDtoDtos= categories.stream()
                .map(cat -> this.modelMapper.map(cat,CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtoDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        categoryRepository.delete(category);
    }

}
