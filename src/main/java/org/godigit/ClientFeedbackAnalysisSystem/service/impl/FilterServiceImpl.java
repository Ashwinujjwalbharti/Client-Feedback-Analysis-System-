package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.godigit.ClientFeedbackAnalysisSystem.service.FilterService;
import org.springframework.stereotype.Service;

@Service
public class FilterServiceImpl implements FilterService {

    private final FeedbackRepository feedbackRepository;

    public FilterServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> filterByDate(LocalDate date) {
        return feedbackRepository.findAll().stream()
        .filter(f -> f.getSubmittedAt().toLocalDate().equals(date))
        .toList();
    }

    @Override
    public List<Feedback> getBySentiment(String sentiment) {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getSentiment() != null && f.getSentiment().equalsIgnoreCase(sentiment))
                .collect(Collectors.toList());
    }

    @Override
    public List<Feedback> filterByCategory(String category) {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getCategory() != null && f.getCategory().toLowerCase()
                        .contains(category.toLowerCase())).toList();
    }
}
