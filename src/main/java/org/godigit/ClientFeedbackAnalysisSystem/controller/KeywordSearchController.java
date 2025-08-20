
package org.godigit.ClientFeedbackAnalysisSystem.controller;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.service.KeywordSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback/search")
public class KeywordSearchController {

    private final KeywordSearchService keywordSearchService;

    public KeywordSearchController(KeywordSearchService keywordSearchService) {
        this.keywordSearchService = keywordSearchService;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> searchFeedbacks(@RequestParam String keyword) {
        List<Feedback> results = keywordSearchService.searchByKeyword(keyword);
        return ResponseEntity.ok(results);
    }
}

