package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.util.List;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.godigit.ClientFeedbackAnalysisSystem.service.FeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackServiceImpl(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return repository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return repository.findAll();
    }

    @Override
    public List<Feedback> getFeedbackByClientName(String clientName) {
        return repository.findAll().stream()
                .filter(f -> f.getClientName().equalsIgnoreCase(clientName))
                .toList();
    }
}
