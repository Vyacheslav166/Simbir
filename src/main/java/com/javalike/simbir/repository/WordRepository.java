package com.javalike.simbir.repository;

import com.javalike.simbir.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
