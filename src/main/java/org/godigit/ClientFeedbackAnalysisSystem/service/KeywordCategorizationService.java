package org.godigit.ClientFeedbackAnalysisSystem.service;

import java.util.List;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

public interface KeywordCategorizationService {
    public List<String> categorizeFeedback(Feedback feedback);
}
