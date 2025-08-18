package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.Utils.SentimentAnalyzer;
import org.springframework.stereotype.Service;

@Service
public class SentimentService {
    private final SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

    public String detectSentiment(String content) {
        if(content == null || content.trim().length() == 0) {
            return "Neutral";
        }
        return sentimentAnalyzer.analyzeSentiment(content);
    }
}
