package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import java.util.List;

public interface FeedbackService {
    public Feedback saveFeedback(Feedback feedback);
    public List<Feedback> getAllFeedback();
    public List<Feedback> getFeedbackByClientName(String clientName);
}

