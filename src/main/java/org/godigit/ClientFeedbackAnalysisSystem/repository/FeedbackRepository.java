package org.godigit.ClientFeedbackAnalysisSystem.repository;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}