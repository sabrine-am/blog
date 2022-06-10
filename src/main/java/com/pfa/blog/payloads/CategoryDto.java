package com.pfa.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotBlank
    @Size(min = 4,message = "title must be minimum 4 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 25,message = "description must be at manimun 25 characters")
    private String categoryDescription;
}
