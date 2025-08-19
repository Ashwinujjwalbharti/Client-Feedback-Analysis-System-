package org.godigit.ClientFeedbackAnalysisSystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
       public List<Feedback> getBySentiment(String sentiment)
    {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getSentiment() != null &&
                             f.getSentiment().equalsIgnoreCase(sentiment))
                .collect(Collectors.toList());
    }

    public List<Feedback> filterByCategory(String category) {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getCategory() != null && f.getCategory()
                        .equals(category)).toList();
    }
}
