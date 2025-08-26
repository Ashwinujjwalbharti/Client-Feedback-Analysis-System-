package org.godigit.ClientFeedbackAnalysisSystem.repository;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCategoryIgnoreCase(String category);
    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    
    @Query("SELECT f FROM Feedback f WHERE LOWER(f.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Feedback> searchByKeyword(@Param("keyword") String keyword);
    
    
    @Query("SELECT f FROM Feedback f WHERE LOWER(f.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    Page<Feedback> findByCategory(@Param("category") String category,Pageable pageable);


    @Query("SELECT f FROM Feedback f WHERE LOWER(f.sentiment) = LOWER(:sentiment)")
    Page<Feedback> findBySentiment(@Param("sentiment") String sentiment, Pageable pageable);


   




}