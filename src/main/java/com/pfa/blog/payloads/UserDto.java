package com.pfa.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotEmpty
    @Size(min = 4,message = "username must be minimum 4 characters")
    private String name;

    @Email(message = "email address is not valid")
    private String email;

    @NotEmpty
    @Size(min=3, max=15,message = "password must be between 3 and 15 characters")
    private String password;

    @NotNull
    private String about;
}
