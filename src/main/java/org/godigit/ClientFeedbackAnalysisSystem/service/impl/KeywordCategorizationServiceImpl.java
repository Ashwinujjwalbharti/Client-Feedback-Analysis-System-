package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordCategorizationService;
import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
import org.springframework.stereotype.Service;

@Service
public class KeywordCategorizationServiceImpl implements KeywordCategorizationService {
    
    private final KeywordCategorizer keywordCategorizer = new KeywordCategorizer();

    @Override
    public String categorizeFeedback(String message) {
        String feedbackMsg = message.toLowerCase();
        String category = keywordCategorizer.getKeywords().entrySet().stream()
        .filter(entry -> entry.getValue().stream()
        .anyMatch(keyword -> feedbackMsg.contains(keyword.toLowerCase())))
        .map(Map.Entry :: getKey)
        .distinct().
        collect(Collectors.joining(","));
        return category;
    }

}
