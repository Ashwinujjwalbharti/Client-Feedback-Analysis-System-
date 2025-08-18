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
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService service;

    private final SentimentService sentimentService;
    @Autowired
    EmojiSentimentService emojiSentimentService;

    public FeedbackController(FeedbackService service, SentimentService sentimentService) {
        this.service = service;
        this.sentimentService = sentimentService;
    }

    @PostMapping("/submitFeedback")
    public ResponseEntity<FeedbackDto> submitFeedback(@RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = FeedbackMapper.toEntity(feedbackDto);
        Feedback saved = service.saveFeedback(feedback);
        return ResponseEntity.ok(FeedbackMapper.toDto(saved));
    }


    @GetMapping("/retrieveFeedback")
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        List<Feedback> feedbacks = service.getAllFeedback();
        List<FeedbackDto> feedbackDtos = feedbacks.stream().map(FeedbackMapper :: toDto).collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDtos);
    }

    @GetMapping("/client")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByClient(@RequestParam String name) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(name);
        List<FeedbackDto> feedbackDtos = feedbacks.stream().map(FeedbackMapper :: toDto).collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDtos);
    }

    @GetMapping("/client/analyze")
    public String analyzeClientFeedback(@RequestParam String name) {
        List<Feedback> feedbackList = service.getFeedbackByClientName(name);
        String feedbacks = feedbackList.stream().map(Feedback :: getMessage).collect(Collectors.joining("\n"));
        return sentimentService.detectSentiment(feedbacks);
    }
    @GetMapping("/emoji-sentiment")
    public ResponseEntity<String> getEmojiSentiment(@RequestParam String text) {
        String result = emojiSentimentService.analyzeEmojiSentiment(text);
        return ResponseEntity.ok(result);
    }

}
