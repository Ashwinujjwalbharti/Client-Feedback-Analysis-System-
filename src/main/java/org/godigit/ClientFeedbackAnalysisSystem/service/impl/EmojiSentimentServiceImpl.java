package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.dto.EmojiSentimentPredictor;
import org.godigit.ClientFeedbackAnalysisSystem.service.EmojiSentimentService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class EmojiSentimentServiceImpl implements EmojiSentimentService {
    
    private final String API_URL = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment";
    private final String API_KEY = "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";

    //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

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
            String json = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            List<List<EmojiSentimentPredictor>> predictions = objectMapper.readValue(json, new TypeReference<>() {});
            EmojiSentimentPredictor topPrediction = predictions.get(0).stream()
            .max((a, b) -> Double.compare(a.getScore(), b.getScore()))
            .orElse(null);

            return (topPrediction == null) ? "Unable to determine sentiment" : switch (topPrediction.getLabel()) {
                case "LABEL_0" -> "Negative";
                case "LABEL_1" -> "Neutral";
                case "LABEL_2" -> "Positive";
                default -> "Unknown";
            };
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
