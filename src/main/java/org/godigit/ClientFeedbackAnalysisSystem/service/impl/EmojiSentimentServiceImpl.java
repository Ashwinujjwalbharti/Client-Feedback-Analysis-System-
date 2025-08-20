package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.service.EmojiSentimentService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmojiSentimentServiceImpl implements EmojiSentimentService {
    
    private final String API_URL = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment";
    private final String API_KEY = "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";

    @Override
    public String analyzeEmojiSentiment(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(API_KEY);

        Map<String, String> body = Map.of("inputs", text);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
