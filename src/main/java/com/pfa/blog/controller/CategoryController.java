package com.pfa.blog.controller;

import com.pfa.blog.payloads.ApiResponse;
import com.pfa.blog.payloads.CategoryDto;
import com.pfa.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blog/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCat=categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer catId){
        CategoryDto updatedCat=categoryService.updateCategory(categoryDto,catId);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("categoryId") Integer catId){
        categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return  ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/oneById/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleUser(@PathVariable("categoryId") Integer catId){
        return  ResponseEntity.ok(categoryService.getCategoryById(catId));
    }
}
