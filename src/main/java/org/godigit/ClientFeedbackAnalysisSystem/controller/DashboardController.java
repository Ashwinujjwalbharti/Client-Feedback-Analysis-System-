package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.DashboardServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class DashboardController {

    private final DashboardServiceImpl service;

    public DashboardController(DashboardServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/dashboard/category")
    public ResponseEntity<List<Feedback>> getPaginatedFeedbacksByCategory(
            @RequestParam String category,
            @RequestParam int page,
            @RequestParam int size) {

        Page<Feedback> feedbackPage = service.getPaginatedFeedbacksByCategory(category, page, size);
        List<Feedback> feedbacks = feedbackPage.getContent();
                                                     
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/dashboard/sentiment")
    public ResponseEntity<List<Feedback>> getPaginatedFeedbacksBySentiment(
            @RequestParam String sentiment,
            @RequestParam int page,
            @RequestParam int size) {

        Page<Feedback> feedbackPage = service.getPaginatedFeedbacksBySentiment(sentiment, page, size);
        List<Feedback> feedbacks = feedbackPage.getContent();
                                                     
        return ResponseEntity.ok(feedbacks);
    }
}
