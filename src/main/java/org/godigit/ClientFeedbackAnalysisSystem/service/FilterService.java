package org.godigit.ClientFeedbackAnalysisSystem.service;

import java.time.LocalDate;
import java.util.List;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    
    private final FeedbackRepository feedbackRepository;

    public FilterService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> filterByDate(LocalDate date) {
        return feedbackRepository.findAll().stream()
        .filter(f -> f.getSubmittedAt().toLocalDate().equals(date))
        .toList();
    }
}
