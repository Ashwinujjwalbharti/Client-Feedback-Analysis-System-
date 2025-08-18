package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public FeedbackDto saveFeedback(FeedbackDto feedback) {
        return repository.save(feedback);
    }

    
    public List<FeedbackDto> getAllFeedback() {
        return repository.findAll();
    }

    public List<FeedbackDto> getFeedbackByClientName(String clientName) {
        return repository.findAll().stream()
                .filter(f -> f.getName().equalsIgnoreCase(clientName))
                .toList();
    }
}

