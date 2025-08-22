

package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordSearchServiceImpl implements KeywordSearchService {

    private final FeedbackRepository feedbackRepository;

    public KeywordSearchServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> searchByKeyword(String keyword) {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        
        List<Feedback> feedbacksWithKeywords = feedbacks.stream()
        .filter(feedback -> (feedback.getCategory() != null 
        && feedback.getCategory().toLowerCase().contains(keyword.toLowerCase())) 
        
        || (feedback.getMessage() != null 
        && feedback.getMessage().toLowerCase().contains(keyword.toLowerCase())) 
        
        || (feedback.getClientName() != null 
        && feedback.getClientName().toLowerCase().contains(keyword.toLowerCase())) 
        
        || (feedback.getSentiment() != null 
        && feedback.getSentiment().toLowerCase().contains(keyword.toLowerCase())))
        .toList();
        
        return feedbacksWithKeywords;
    }
}
