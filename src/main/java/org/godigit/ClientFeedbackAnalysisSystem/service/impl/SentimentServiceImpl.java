package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.service.SentimentService;
import org.godigit.ClientFeedbackAnalysisSystem.utils.SentimentAnalyzer;
import org.springframework.stereotype.Service;

@Service
public class SentimentServiceImpl implements SentimentService {

    private final SentimentAnalyzer sentimentAnalyzer;

    public SentimentServiceImpl(SentimentAnalyzer sentimentAnalyzer) {
        this.sentimentAnalyzer = sentimentAnalyzer;
    }

    @Override
    public String detectSentiment(String content) {
        if(content == null || content.trim().length() == 0) {
            return "Feedback is empty.";
        }
        return sentimentAnalyzer.analyzeSentiment(content);
    }
}
