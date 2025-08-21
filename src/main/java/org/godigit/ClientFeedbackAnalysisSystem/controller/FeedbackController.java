package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackCategoryUpdate;
import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackMessageUpdate;
import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackSentimentUpdate;
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

    @GetMapping("/update/name")
    public ResponseEntity<List<Feedback>> updateClientName(@RequestParam String name, String newName) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(name);
        
        feedbacks.stream()
        .filter(feedback -> feedback != null)
        .forEach(feedback -> feedback.setClientName(newName));
        
        feedbacks.forEach(service :: saveFeedback);

        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/update/message")
    public ResponseEntity<List<Feedback>> updateClientFeedbackMessage(@RequestBody FeedbackMessageUpdate feedbackMessageUpdate) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(feedbackMessageUpdate.getName());
        
        feedbacks.stream()
        .filter(feedback -> feedback != null && feedback.getId() == feedbackMessageUpdate.getId())
        .forEach(feedback -> feedback.setMessage(feedbackMessageUpdate.getMessage()));
        
        feedbacks.stream()
        .filter(feedback -> feedback != null && feedback.getId() == feedbackMessageUpdate.getId())
        .forEach(feedback -> feedback.setCategory(keywordCategorizationServiceImpl.categorizeFeedback(feedbackMessageUpdate.getMessage())));
        
        feedbacks.stream()
        .filter(feedback -> feedback != null && feedback.getId() == feedbackMessageUpdate.getId())
        .forEach(feedback -> feedback.setSentiment(sentimentService.detectSentiment(feedbackMessageUpdate.getMessage())));

        feedbacks.forEach(service :: saveFeedback);

        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/update/category")
    public ResponseEntity<List<Feedback>> updateClientFeedbackCategory(@RequestBody FeedbackCategoryUpdate feedbackCategoryUpdate) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(feedbackCategoryUpdate.getName());

        feedbacks.stream()
        .filter(feedback -> feedback != null && feedback.getId() == feedbackCategoryUpdate.getId() && feedback.getClientName().equalsIgnoreCase(feedbackCategoryUpdate.getName()))
        .forEach(feedback -> feedback.setCategory(feedbackCategoryUpdate.getCategory()));
        
        feedbacks.forEach(service :: saveFeedback);

        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/update/sentiment")
    public ResponseEntity<List<Feedback>> updateClientFeedbackSentiment(@RequestBody FeedbackSentimentUpdate feedbackSentimentUpdate) {
        List<Feedback> feedbacks = service.getFeedbackByClientName(feedbackSentimentUpdate.getName());

        feedbacks.stream()
        .filter(feedback -> feedback != null && feedback.getId() == feedbackSentimentUpdate.getId() && feedback.getClientName().equalsIgnoreCase(feedbackSentimentUpdate.getName()))
        .forEach(feedback -> feedback.setSentiment(feedbackSentimentUpdate.getSentiment()));
        
        feedbacks.forEach(service :: saveFeedback);

        return ResponseEntity.ok(feedbacks);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllClientFeedbacks() {
        return ResponseEntity.ok(service.deleteFeedbacks());
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deleteClientFeedback(@RequestParam String name) {
        return ResponseEntity.ok(service.deleteClientFeedback(name));
    }

    @DeleteMapping("/delete/category")
    public ResponseEntity<String> deleteFeedbacksByCategory(@RequestParam String category) {
        return ResponseEntity.ok(service.deleteFeedbackByCategory(category));
    }

    @DeleteMapping("/delete/sentiment")
    public ResponseEntity<String> deleteFeedbacksBySentiment(@RequestParam String sentiment) {
        return ResponseEntity.ok(service.deleteFeedbackBySentiment(sentiment));
    }

    @GetMapping("/client/sentiment")
    public ResponseEntity<String> analyzeClientFeedback(@RequestParam String name) {
        List<Feedback> feedbackList = service.getFeedbackByClientName(name);
        
        String feedbacks = feedbackList.stream()
        .map(Feedback :: getMessage)
        .collect(Collectors.joining("\n"));
        
        return ResponseEntity.ok(sentimentService.detectSentiment(feedbacks));
    }

    @GetMapping("/client/category")
    public ResponseEntity<String> getFeedbackCategory(@RequestParam String name) {
        List<Feedback> feedbackList = service.getFeedbackByClientName(name);
        
        String feedbacks = feedbackList.stream()
        .map(Feedback :: getMessage)
        .collect(Collectors.joining("\n"));
        
        return ResponseEntity.ok(keywordCategorizationServiceImpl.categorizeFeedback(feedbacks));
    }
    

    @GetMapping("/client/emoji-sentiment")
    public ResponseEntity<String> getEmojiSentiment(@RequestParam String text) {
        String result = emojiSentimentServiceImpl.analyzeEmojiSentiment(text);
        return ResponseEntity.ok(result);
    }
    
}
