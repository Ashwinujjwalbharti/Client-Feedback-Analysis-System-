package org.godigit.ClientFeedbackAnalysisSystem.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class KeywordCategorizer {
    
    private final Map<String, List<String>> keywords = new HashMap<>();

    public KeywordCategorizer() {
        
        keywords.put("Time", Arrays.asList("delay", "late", "fast", "slow", "wait", "schedule", "timing"));
        
        keywords.put("Delivery", Arrays.asList("tracking", "delivered", "missing", "packaging"));
        
        keywords.put("Billing", Arrays.asList("billing", "invoice", "refund", "payment", "cost", "price", "charge"));
        
        keywords.put("Payment", Arrays.asList("refund", "payment", "invoice", "charge"));
        
        keywords.put("Support", Arrays.asList("support", "help", "response", "call", "email"));
        
        keywords.put("Service", Arrays.asList("rude", "polite", "friendly"));
        
        keywords.put("Product", Arrays.asList("quality", "broken", "faulty", "working", "defective", "durable", "poor", "excellent"));
        
        keywords.put("Technical", Arrays.asList("bug", "error", "crash", "glitch", "update", "login", "loading"));
        
        keywords.put("Order", Arrays.asList("order", "cancel", "return", "exchange", "product"));
        
        keywords.put("Experience", Arrays.asList("feedback", "service", "usage", "interface", "navigation", "onboarding", "installation", "setup"));
        
        keywords.put("Sentiment", Arrays.asList("satisfied", "happy", "disappointed", "angry", "frustrated", "impressed", "annoyed", "grateful"));

    }

    public Map<String, List<String>> getKeywords() {
        return keywords;
    }
}
