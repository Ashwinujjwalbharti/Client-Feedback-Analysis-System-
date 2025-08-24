package org.godigit.ClientFeedbackAnalysisSystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.FilterServiceImpl;
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
    private FilterServiceImpl filterService;

    @GetMapping("/date")
    public ResponseEntity<List<Feedback>> filterFeedbackByDate(@RequestParam String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Feedback> feedbacks = filterService.filterByDate(date);
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/sentiment")
    public ResponseEntity<List<Feedback>> filterFeedbackBySentiment(@RequestParam String sentiment) {
        List<Feedback> feedbacks = filterService.getBySentiment(sentiment);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Feedback>> filterFeedbackByCategory(@RequestParam String category) {
        List<Feedback> feedbacks = filterService.filterByCategory(category);
        return ResponseEntity.ok(feedbacks);
    }

}
