

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
        return feedbackRepository.searchByKeyword(keyword);
    }
}
