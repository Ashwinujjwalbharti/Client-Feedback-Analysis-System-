package org.godigit.ClientFeedbackAnalysisSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FilterService {

    private final FeedbackRepository repository;

    public FilterService(FeedbackRepository repository) {
        this.repository = repository;
    }
    public List<Feedback> getBySentiment(String sentiment)
    {
        return repository.findAll().stream()
                .filter(f -> f.getSentiment() != null &&
                             f.getSentiment().equalsIgnoreCase(sentiment))
                .collect(Collectors.toList());
    }
    
}
