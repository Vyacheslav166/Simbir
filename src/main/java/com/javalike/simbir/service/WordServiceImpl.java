package com.javalike.simbir.service;

import com.javalike.simbir.model.Url;
import com.javalike.simbir.model.Word;
import com.javalike.simbir.repository.WordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordServiceImpl implements WordService{

    private static final Logger logger = LogManager.getLogger(WordServiceImpl.class);
    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    /**
     * Парсит все слова с HTML-страницы
     *
     * @param url Объект Url, содержащий адрес HTML-страницы
     */
    @Override
    public void parseWord(Url url) {
        try {
            Document document = Jsoup.connect(url.getUrl())
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .timeout(5000)
                    .referrer("https://www.google.com")
                    .get();
            Pattern pattern =
                    Pattern.compile("[A-Za-zА-Яа-я]+", Pattern.UNICODE_CHARACTER_CLASS
                            | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(document.text());
            while (matcher.find()) {
                String word = matcher.group().toUpperCase();
                Long id = isExist(word);
                if(id == null) {
                    Word obj = new Word();
                    obj.setWord(word);
                    obj.setCount(1);
                    saveWord(obj);
                } else {
                    incrementCount(id);
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }

    }

    /**
     * Сохраняет слово
     *
     * @param word
     * @return
     */
    @Override
    public Word saveWord(Word word) {
        return wordRepository.save(word);
    }

    /**
     * Удаляет все слова
     */
    @Override
    public void deleteAll() {
        wordRepository.deleteAll();
    }

    /**
     * Удаляет одно слово
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        wordRepository.deleteById(id);
    }

    /**
     * Возвращает список всех слов
     *
     * @return
     */
    @Override
    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    /**
     * Возвращает слово
     *
     * @param id
     * @return
     */
    @Override
    public Word getById(Long id) {
        return wordRepository.getById(id);
    }

    /**
     * Проверяет, есть ли уже данное слово в базе данных
     *
     * @param wordValue Слово, которое проверяем
     * @return Id слова при наличии. Если слово отсутствует в базе данных, возвращает <code>null</code>
     */
    @Override
    public Long isExist(String wordValue) {
        List<Word> wordsList = wordRepository.findAll();
        Long id = null;
        for (Word word : wordsList) {
            if(word.getWord().equals(wordValue)) {
                id = word.getId();
            }
        }
        return id;
    }

    /**
     * Для заданного по ID слова, увеличивает значение поля <code>count</code> на единицу
     *
     * @param id
     */
    @Override
    public void incrementCount(Long id) {
        Word word = wordRepository.getById(id);
        word.setCount(word.getCount() + 1);
    }
}
