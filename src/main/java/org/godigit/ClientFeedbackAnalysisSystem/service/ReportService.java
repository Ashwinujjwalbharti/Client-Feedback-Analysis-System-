package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

import java.util.*;

public interface ReportService {
    public Map<String, Object> generateReport(int weeks);
    public Map<String, Long> getRecurringIssues(List<Feedback> feedbacks);
    public Map<String, Long> getSentimentTrends(List<Feedback> feedbacks);
}