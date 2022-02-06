package com.javalike.simbir.service;

import com.javalike.simbir.model.SiteUrl;
import com.javalike.simbir.model.Word;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordService {
    void parseWord(SiteUrl url);
    Word saveWord(Word word);
    void deleteAll();
    void deleteById(Long id);
    List<Word> getAll();
    Word getById(Long id);
    Long isExist(String wordValue);
    void incrementCount(Long id);

}
