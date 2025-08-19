package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/feedback")
public class FilterController {
    
    @Autowired
    private FilterService fservice;


    public FilterController(FilterService fservice) {
        this.fservice = fservice;
    }

    @GetMapping("/filterBySentiment")
    public ResponseEntity<List<FeedbackDto>> filterFeedbackBySentiment(@RequestParam String sentiment) {
        List<Feedback> feedbacks = fservice.getBySentiment(sentiment);
        List<FeedbackDto> dtos = feedbacks.stream()
                                          .map(FeedbackMapper::toDto)
                                          .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}



