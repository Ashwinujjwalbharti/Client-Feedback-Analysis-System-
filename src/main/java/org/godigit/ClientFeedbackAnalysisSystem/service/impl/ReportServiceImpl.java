package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl {
    
    private final FeedbackRepository feedbackRepository;

    public ReportServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Map<String, Object> generateReport(int weeks) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minus(weeks, ChronoUnit.WEEKS);

        List<Feedback> feedbacks = feedbackRepository.findBySubmittedAtBetween(startDate, endDate);

        Map<String, Long> recurringIssues = getRecurringIssues(feedbacks);
        Map<String, Long> sentimentTrends = getSentimentTrends(feedbacks);

        Map<String, Object> report = new HashMap<>();
        report.put("recurringIssues", recurringIssues);
        report.put("sentimentTrends", sentimentTrends);

        return report;
    }

    private Map<String, Long> getRecurringIssues(List<Feedback> feedbacks) {
        Map<String, Long> issuesMap = new HashMap<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getCategory() != null) {
                issuesMap.put(feedback.getCategory(), issuesMap.getOrDefault(feedback.getCategory(), 0L) + 1);
            }
        }
        return issuesMap;
    }

    private Map<String, Long> getSentimentTrends(List<Feedback> feedbacks) {
        Map<String, Long> trendsMap = new HashMap<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getSentiment() != null) {
                trendsMap.put(feedback.getSentiment(), trendsMap.getOrDefault(feedback.getSentiment(), 0L) + 1);
            }
        }
        return trendsMap;
    }
}
