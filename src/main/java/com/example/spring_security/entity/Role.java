package com.example.spring_security.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
