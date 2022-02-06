package com.javalike.simbir.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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

    public void incrementCount() {
        setCount(Objects.requireNonNullElse(getCount(), 0) + 1);
    }

    public static Word getInstance() {
        return new Word();
    }

    public Word withWord(String word) {
        this.word = word;
        return this;
    }

    public Word withCount(int count) {
        this.count = count;
        return this;
    }
}
