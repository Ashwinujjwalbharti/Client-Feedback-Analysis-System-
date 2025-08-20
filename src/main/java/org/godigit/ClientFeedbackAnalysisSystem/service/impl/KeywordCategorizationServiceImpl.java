package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.util.List;
import java.util.Map;

import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordCategorizationService;
import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
import org.springframework.stereotype.Service;

@Service
public class KeywordCategorizationServiceImpl implements KeywordCategorizationService {
    
    private final KeywordCategorizer keywordCategorizer = new KeywordCategorizer();

    @Override
    public String categorizeFeedback(String message) {
        String category = "";
        Map<String, List<String>> keywords = keywordCategorizer.getKeywords();
        String feedbackMsg = message.toLowerCase();
        for(Map.Entry<String, List<String>> entry : keywords.entrySet()) {
            for(String keyword : entry.getValue()) {
                if(feedbackMsg.contains(keyword.toLowerCase())) {
                    category += entry.getKey() + ",";
                }
            }
        }
        int n = category.length();
        return (n == 0) ? category : category.substring(0, n - 1);
    }

}
