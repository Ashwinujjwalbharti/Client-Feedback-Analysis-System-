package org.godigit.ClientFeedbackAnalysisSystem.repository;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCategoryIgnoreCase(String category);
    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    
    @Query("SELECT f FROM Feedback f WHERE LOWER(f.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Feedback> searchByKeyword(@Param("keyword") String keyword);

}