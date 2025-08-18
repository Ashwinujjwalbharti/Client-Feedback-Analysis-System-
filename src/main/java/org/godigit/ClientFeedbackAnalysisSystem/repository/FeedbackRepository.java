package org.godigit.ClientFeedbackAnalysisSystem.repository;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackDto, Long> {
}