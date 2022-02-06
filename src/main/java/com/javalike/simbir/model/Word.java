package com.javalike.simbir.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wordfromsite")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "word")
    private String word;

    @Column(name = "count")
    private Integer count;
}
