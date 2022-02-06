package com.javalike.simbir.service;

import com.javalike.simbir.model.SiteUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordProducerService implements ParseSiteService {

    @Override
    public List<String> parseSite(SiteUrl siteUrl) throws IOException {
        Document document = Jsoup.connect(siteUrl.getUrl())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .timeout(5000)
                .referrer("https://www.google.com")
                .get();
        Pattern pattern =
                Pattern.compile("[A-Za-zА-Яа-я]+", Pattern.UNICODE_CHARACTER_CLASS
                        | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(document.text());
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group().toUpperCase());
        }
        return result;
    }
}
