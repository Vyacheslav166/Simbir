package com.javalike.simbir.controller;

import com.javalike.simbir.model.SiteUrl;
import com.javalike.simbir.model.Word;
import com.javalike.simbir.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * Отображает список всех слов
     *
     * @param model Модель для добавления атрибутов в модель для отображения представления
     * @return HTML страницу со списком слов
     */
    @GetMapping("/words")
    public String getAll(Model model) {
        List<Word> words = wordService.getAll();
        model.addAttribute("words", words);
        return "word-list";
    }

    /**
     * Отображает форму для ввода URL HTML-страницы
     *
     * @param url Объект Url, содержащий адрес HTML-страницы
     * @return Форму для ввода URL HTML-страницы
     */
    @GetMapping("/word-parsing")
    public String parsingWordForm(SiteUrl url) {
        return "word-parsing";
    }

    /**
     * Парсит все слова с HTML-страницы
     *
     * @param url Объект Url, содержащий адрес HTML-страницы
     * @return HTML страницу со списком слов
     */
    @PostMapping("/word-parsing")
    public String parsingWord(SiteUrl url) {
        wordService.parseWord(url);
        return "redirect:/words";
    }

    /**
     * Удаляет одно слово
     *
     * @param id
     * @return HTML страницу со списком слов
     */
    @GetMapping("word-delete/{id}")
    public String deleteWord(@PathVariable("id") Long id) {
        wordService.deleteById(id);
        return "redirect:/words";
    }

    /**
     * Удаляет все слова
     *
     * @return HTML страницу со списком слов
     */
    @GetMapping("/word-delete")
    public String deleteAllWords() {
        wordService.deleteAll();
        return "redirect:/words";
    }
}
