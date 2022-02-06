package com.javalike.simbir.model;

import org.aspectj.weaver.World;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @DisplayName("Test increment word count")
    @Test
    public void incrementCount() {
        Word word = new Word();
        word.incrementCount();
        assertEquals(word.getCount(), 1, "Count word can not equal 1");
    }

    @Test
    public void withWord() {
        Word word = new Word();
        word.withWord("TEST");

        assertEquals(word.getWord(), "TEST", "Word can not equal 'TEST'");
    }

    @Test
    public void withCount() {
        Word word = new Word();
        word.withCount(1);
        assertEquals(word.getCount(), 1);
    }
}