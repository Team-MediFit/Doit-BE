package com.medicarebe.quiz.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("SENTENCE_CONSTRUCTION")
public class SentenceConstructionQuiz extends Quiz{

    // 정답 문장 (e.g., "안녕하세요 저는 도두입니다.")
    @Column(name = "correct_sentence", nullable = false)
    private String correctSentence;

    // 선택지로 주어지는 단어 뭉치 (JSON 배열 형태)
    // e.g., ["안녕하세요", "안녕", "저는", "누구", "입니다", "이름", "잘가"]
    @Column(name = "word_bank", columnDefinition = "json")
    private String wordBank;

    // 정답 문장 음성 파일 URL (선택 사항)
    @Column(name = "audio_url")
    private String audioUrl;

    @Override
    public QuizType getQuizType() {
        return QuizType.SENTENCE_CONSTRUCTION;
    }

    @Override
    public Map<String, Object> getDetails(ObjectMapper objectMapper) {
        Map<String, Object> detailsMap = new LinkedHashMap<>();
        try {
            detailsMap.put("correctSentence", this.correctSentence);
            detailsMap.put("wordBank", objectMapper.readValue(this.wordBank, new TypeReference<List<String>>() {}));
            detailsMap.put("audioUrl", this.audioUrl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string from entity", e);
        }
        return detailsMap;
    }
}
