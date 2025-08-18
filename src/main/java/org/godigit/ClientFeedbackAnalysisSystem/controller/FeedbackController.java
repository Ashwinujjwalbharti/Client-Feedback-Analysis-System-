package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.EmojiSentimentService;
import org.godigit.ClientFeedbackAnalysisSystem.service.FeedbackService;
import org.godigit.ClientFeedbackAnalysisSystem.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    EmojiSentimentService emojiSentimentService;


    @PostMapping("/submitFeedback")
    public ResponseEntity<Feedback> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        Feedback saved = service.saveFeedback(feedbackDto); // pass DTO to service
        return ResponseEntity.ok(saved);
    }



    @GetMapping("/retrieveFeedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(service.getAllFeedback());
    }

    @GetMapping("/client")
    public ResponseEntity<List<Feedback>> getFeedbackByClient(@RequestParam String name) {
        return ResponseEntity.ok(service.getFeedbackByClientName(name));
    }

    @GetMapping("/client/analyze")
    public String analyzeClientFeedback(@RequestParam String name) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(name);
        String feedback = "";
        for(Feedback i : feedbacks) {
            feedback += i.getMessage() + "/n";
        }
        return sentimentService.detectSentiment(feedback);
    }


    @GetMapping("/emoji-sentiment")
    public ResponseEntity<String> getEmojiSentiment(@RequestParam String text) {
        String result = emojiSentimentService.analyzeEmojiSentiment(text);
        return ResponseEntity.ok(result);
    }


}

