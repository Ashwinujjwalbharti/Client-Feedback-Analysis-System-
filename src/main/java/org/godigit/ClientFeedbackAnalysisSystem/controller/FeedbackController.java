package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.service.FeedbackService;
import org.godigit.ClientFeedbackAnalysisSystem.service.SentimentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService service;

    private final SentimentService sentimentService;

    public FeedbackController(FeedbackService service, SentimentService sentimentService) {
        this.service = service;
        this.sentimentService = sentimentService;
    }

    @PostMapping("/submitFeedback")
    public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedback) {
        FeedbackDto saved = service.saveFeedback(feedback);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/retrieveFeedback")
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        return ResponseEntity.ok(service.getAllFeedback());
    }

    @GetMapping("/client")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByClient(@RequestParam String name) {
        return ResponseEntity.ok(service.getFeedbackByClientName(name));
    }

    @GetMapping("/client/analyze")
    public String analyzeClientFeedback(@RequestParam String name) {
        List<FeedbackDto> feedbacks = service.getFeedbackByClientName(name);
        String feedback = "";
        for(FeedbackDto i : feedbacks) {
            feedback += i.getMessage() + "/n";
        }
        return sentimentService.detectSentiment(feedback);
    }
    
}

