package com.javalike.simbir.service;

import com.javalike.simbir.model.SiteUrl;
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

@Service
public class WordServiceImpl implements WordService{

    private static final Logger logger = LogManager.getLogger(WordServiceImpl.class);
    private final WordRepository wordRepository;
    private final ParseSiteService parseSiteService;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository, ParseSiteService parseSiteService) {
        this.wordRepository = wordRepository;
        this.parseSiteService = parseSiteService;
    }

    /**
     * Парсит все слова с HTML-страницы
     *
     * @param siteUrl Объект SiteUrl, содержащий адрес HTML-страницы
     */
    @Override
    public void parseWord(SiteUrl siteUrl) {
        try {
            List<String> siteWordList = parseSiteService.parseSite(siteUrl);
            for (String siteWord : siteWordList) {
                Long id = isExist(siteWord);
                if(id == null) {
                    saveWord(Word.getInstance().withWord(siteWord).withCount(1));
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
        return wordRepository.findByWord(wordValue).map(Word::getId).orElse(null);
    }

    /**
     * Для заданного по ID слова, увеличивает значение поля <code>count</code> на единицу
     *
     * @param id
     */
    @Override
    public void incrementCount(Long id) {
        Word word = wordRepository.getById(id);
        word.incrementCount();
        wordRepository.save(word);
    }
}
