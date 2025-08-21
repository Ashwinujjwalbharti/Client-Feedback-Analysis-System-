
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
    public ResponseEntity<?> searchFeedbacks(@RequestParam String keyword) {
        if(keyword.isEmpty() || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Keyword must not be empty.");
        }

        List<Feedback> results = keywordSearchService.searchByKeyword(keyword);
        
        if(results.isEmpty()) {
            return ResponseEntity.status(404).body("No feedbacks found for keyword: " + keyword);
        }

        return ResponseEntity.ok(results);
    }
}

