package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback saveFeedback(FeedbackDto dto) {
        Feedback feedback = FeedbackMapper.toEntity(dto); // conversion here
        return repository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return repository.findAll();
    }

    public List<Feedback> getFeedbackByClientName(String clientName) {
        return repository.findAll().stream()
                .filter(f -> f.getClientName().equalsIgnoreCase(clientName))
                .toList();
    }
}

