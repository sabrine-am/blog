package com.pfa.blog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
    @Id
    private int id;

    private String name;
}
