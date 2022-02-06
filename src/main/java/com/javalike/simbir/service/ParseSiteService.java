package com.javalike.simbir.service;

import com.javalike.simbir.model.SiteUrl;

import java.io.IOException;
import java.util.List;

public interface ParseSiteService {
    List<String> parseSite(SiteUrl url) throws IOException;
}
