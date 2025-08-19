package org.godigit.ClientFeedbackAnalysisSystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback/filter")
public class FilterController {
    
    @Autowired
    private FilterService filterService;

    @GetMapping("/date")
    public ResponseEntity<List<FeedbackDto>> filterFeedbackByDate(@RequestParam String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Feedback> feedbacks = filterService.filterByDate(date);
        List<FeedbackDto> feedbackDtos = feedbacks.stream().map(FeedbackMapper :: toDto).collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDtos);
    }
    
    @GetMapping("/sentiment")
    public ResponseEntity<List<FeedbackDto>> filterFeedbackBySentiment(@RequestParam String sentiment) {
        List<Feedback> feedbacks = filterService.getBySentiment(sentiment);
        List<FeedbackDto> dtos = feedbacks.stream().map(FeedbackMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/category")
    public ResponseEntity<List<FeedbackDto>> filterFeedbackByCategory(@RequestParam String category) {
        List<Feedback> feedbacks = filterService.filterByCategory(category);
        List<FeedbackDto> feedbackDtos = feedbacks.stream().map(FeedbackMapper :: toDto).collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDtos);
    }

}
