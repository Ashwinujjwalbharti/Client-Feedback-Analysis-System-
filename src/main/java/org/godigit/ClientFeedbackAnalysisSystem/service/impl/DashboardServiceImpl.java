package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.godigit.ClientFeedbackAnalysisSystem.service.DashboardService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final FeedbackRepository repository;

    public DashboardServiceImpl(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Feedback> getPaginatedFeedbacks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Page<Feedback> getPaginatedFeedbacksByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByCategory(category, pageable);
    }
}
