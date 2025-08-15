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
@DiscriminatorValue("FILL_IN_THE_BLANK")
public class FillInTheBlankQuiz extends Quiz {

    // 빈칸 앞 부분 문장 (e.g., "안녕하세요, 제 이름")
    @Column(name = "pre_blank_text")
    private String preBlankText;

    // 빈칸 뒷 부분 문장 (e.g., "앤드류입니다.")
    @Column(name = "post_blank_text")
    private String postBlankText;

    // 빈칸에 들어갈 선택지 (JSON 배열 형태)
    // e.g., ["은", "는", "가"]
    @Column(name = "choices", columnDefinition = "json", nullable = false)
    private String choices;

    // 빈칸의 정답
    @Column(name = "answer", nullable = false)
    private String answer;

    @Override
    public QuizType getQuizType() {
        return QuizType.FILL_IN_THE_BLANK;
    }

    @Override
    public Map<String, Object> getDetails(ObjectMapper objectMapper) {
        Map<String, Object> detailsMap = new LinkedHashMap<>();
        try {
            detailsMap.put("preBlankText", this.getPreBlankText());
            detailsMap.put("postBlankText", this.getPostBlankText());
            detailsMap.put("choices", objectMapper.readValue(this.getChoices(), new TypeReference<List<String>>() {}));
            detailsMap.put("answer", this.getAnswer());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string from entity", e);
        }
        return detailsMap;
    }
}
