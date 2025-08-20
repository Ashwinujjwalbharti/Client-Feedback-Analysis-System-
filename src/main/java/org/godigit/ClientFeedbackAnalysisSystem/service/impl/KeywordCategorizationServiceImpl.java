package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordCategorizationService;
import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
import org.springframework.stereotype.Service;

@Service
public class KeywordCategorizationServiceImpl implements KeywordCategorizationService {
    
    private final KeywordCategorizer keywordCategorizer = new KeywordCategorizer();

    @Override
    public List<String> categorizeFeedback(Feedback feedback) {
        List<String> categoryList = new ArrayList<>();
        Map<String, List<String>> keywords = keywordCategorizer.getKeywords();
        String feedbackMsg = feedback.getMessage().toLowerCase();
        for(Map.Entry<String, List<String>> entry : keywords.entrySet()) {
            for(String keyword : entry.getValue()) {
                if(feedbackMsg.contains(keyword.toLowerCase())) {
                    categoryList.add(entry.getKey());
                }
            }
        }
        return categoryList;
    }

}
