package org.godigit.ClientFeedbackAnalysisSystem.service;

import java.time.LocalDate;
import java.util.List;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

public interface FilterService {
    public List<Feedback> filterByDate(LocalDate date);
    public List<Feedback> getBySentiment(String sentiment);
    public List<Feedback> filterByCategory(String category);
}
