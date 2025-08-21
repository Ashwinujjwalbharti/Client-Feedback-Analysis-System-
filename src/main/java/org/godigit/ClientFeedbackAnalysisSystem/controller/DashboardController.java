package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.mapper.FeedbackMapper;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.impl.DashboardServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class DashboardController {

    private final DashboardServiceImpl service;

    public DashboardController(DashboardServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<FeedbackDto>> getPaginatedFeedbacksByCategory(
            @RequestParam String category,
            @RequestParam int page,
            @RequestParam int size) {

        Page<Feedback> feedbackPage = service.getPaginatedFeedbacksByCategory(category, page, size);
        List<FeedbackDto> feedbackDtos = feedbackPage.getContent()
                                                     .stream()
                                                     .map(FeedbackMapper::toDto)
                                                     .collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDtos);
    }
}
