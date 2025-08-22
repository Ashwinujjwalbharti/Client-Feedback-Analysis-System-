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

    @Override
    public String deleteClientFeedback(String name) {
        List<Feedback> feedbacks = getFeedbackByClientName(name);
        if(feedbacks.isEmpty()) return "There are no feedbacks.";
        repository.deleteAll(feedbacks);
        return "Client Feedbacks deleted successfully.";
    }

    @Override
    public String deleteFeedbacks() {
        repository.deleteAll();
        return "All the client feedbacks deleted successully.";
    }

    @Override
    public String deleteFeedbackByCategory(String category) {
        List<Feedback> feedbacks = repository.findAll().stream()
        .filter(feedback -> feedback != null && feedback.getCategory().toLowerCase().contains(category.toLowerCase()))
        .toList();
        repository.deleteAll(feedbacks);
        return "Category based client feedbacks have been successfully deleted.";
    }

    @Override
    public String deleteFeedbackBySentiment(String sentiment) {
        List<Feedback> feedbacks = repository.findAll().stream()
        .filter(feedback -> feedback != null && feedback.getSentiment().equalsIgnoreCase(sentiment))
        .toList();
        repository.deleteAll(feedbacks);
        return "Sentiment based client feedbacks have been successfully deleted.";
    }
}
