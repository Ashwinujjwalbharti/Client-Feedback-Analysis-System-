

package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import java.util.List;

public interface KeywordSearchService {
    List<Feedback> searchByKeyword(String keyword);
}

