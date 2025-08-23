package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordCategorizationService;
import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
import org.springframework.stereotype.Service;

@Service
public class KeywordCategorizationServiceImpl implements KeywordCategorizationService {

    private final KeywordCategorizer keywordCategorizer;

    public KeywordCategorizationServiceImpl(KeywordCategorizer keywordCategorizer) {
        this.keywordCategorizer = keywordCategorizer;
    }

    @Override
    public String categorizeFeedback(String message) {
        if (message == null || message.isBlank()) {
            return "";
        }

        String feedbackMsg = message.toLowerCase();
        String category = keywordCategorizer.getKeywords().entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(keyword -> feedbackMsg.contains(keyword.toLowerCase())))
                .map(Map.Entry::getKey)
                .distinct().collect(Collectors.joining(","));
        return category;
    }

}
