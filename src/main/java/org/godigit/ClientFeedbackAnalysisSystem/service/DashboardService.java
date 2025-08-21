package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

import org.springframework.data.domain.Page;

public interface DashboardService {
    Page<Feedback> getPaginatedFeedbacks(int page, int size);
    Page<Feedback> getPaginatedFeedbacksByCategory(String category,int page, int size);
}

