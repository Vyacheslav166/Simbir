package com.javalike.simbir.service;

import com.javalike.simbir.model.Url;
import com.javalike.simbir.model.Word;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WordService {
    public void parseWord(Url url);
    public Word saveWord(Word word);
    public void deleteAll();
    public void deleteById(Long id);
    public List<Word> getAll();
    public Word getById(Long id);
    public Long isExist(String wordValue);
    public void incrementCount(Long id);

}
