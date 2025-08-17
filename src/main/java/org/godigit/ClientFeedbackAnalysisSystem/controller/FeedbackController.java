package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class FeedbackController {
    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        Feedback saved = service.saveFeedback(feedback);
        return ResponseEntity.ok(saved);
    }
}
