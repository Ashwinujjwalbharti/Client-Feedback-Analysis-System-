package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.EmojiSentimentServiceImpl;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.FeedbackServiceImpl;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.KeywordCategorizationServiceImpl;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.SentimentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackServiceImpl service;

    private final SentimentServiceImpl sentimentService;

    @Autowired
    private EmojiSentimentServiceImpl emojiSentimentServiceImpl;

    @Autowired
    private KeywordCategorizationServiceImpl keywordCategorizationServiceImpl;

    public FeedbackController(FeedbackServiceImpl service, SentimentServiceImpl sentimentService) {
        this.service = service;
        this.sentimentService = sentimentService;
    }

    @PostMapping("/submit")
    public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = FeedbackMapper.toEntity(feedbackDto);
        feedback.setSentiment(sentimentService.detectSentiment(feedback.getMessage()));;
        feedback.setCategory(keywordCategorizationServiceImpl.categorizeFeedback(feedback.getMessage()));
        Feedback saved = service.saveFeedback(feedback);
        return ResponseEntity.ok(FeedbackMapper.toDto(saved));
    }


    @GetMapping("/retrieve")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = service.getAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/retrieve/client")
    public ResponseEntity<List<Feedback>> getFeedbackByClient(@RequestParam String name) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(name);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/client/analyze")
    public String analyzeClientFeedback(@RequestParam String name) {
        List<Feedback> feedbackList = service.getFeedbackByClientName(name);
        String feedbacks = feedbackList.stream().map(Feedback :: getMessage).collect(Collectors.joining("\n"));
        return sentimentService.detectSentiment(feedbacks);
    }

    @GetMapping("/client/categorize")
    public String getFeedbackCategory(@RequestParam String name) {
        List<Feedback> feedbackList = service.getFeedbackByClientName(name);
        String feedbacks = feedbackList.stream().map(Feedback :: getMessage).collect(Collectors.joining("\n"));
        return keywordCategorizationServiceImpl.categorizeFeedback(feedbacks);
    }
    

    @GetMapping("/emoji-sentiment")
    public ResponseEntity<String> getEmojiSentiment(@RequestParam String text) {
        String result = emojiSentimentServiceImpl.analyzeEmojiSentiment(text);
        return ResponseEntity.ok(result);
    }
    
}
