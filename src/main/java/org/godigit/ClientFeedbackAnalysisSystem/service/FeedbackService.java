package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;

public class FeedbackService {
    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback saveFeedback(Feedback feedback) {
        return repository.save(feedback);
    }
}
