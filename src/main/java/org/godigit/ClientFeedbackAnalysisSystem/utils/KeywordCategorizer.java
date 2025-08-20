package org.godigit.ClientFeedbackAnalysisSystem.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordCategorizer {
    
    private final Map<String, List<String>> keywords = new HashMap<>();

    public KeywordCategorizer() {

        Map<String, List<String>> keywordMap = new HashMap<>();
        
        keywordMap.put("Time", Arrays.asList("delay", "late", "fast", "slow", "wait", "schedule", "timing"));
        
        keywordMap.put("Delivery", Arrays.asList("tracking", "delivered", "missing", "packaging"));
        
        keywordMap.put("Billing", Arrays.asList("billing", "invoice", "refund", "payment", "cost", "price", "charge"));
        
        keywordMap.put("Payment", Arrays.asList("refund", "payment", "invoice", "charge"));
        
        keywordMap.put("Support", Arrays.asList("support", "help", "response", "call", "email"));
        
        keywordMap.put("Service", Arrays.asList("rude", "polite", "friendly"));
        
        keywordMap.put("Product", Arrays.asList("quality", "broken", "faulty", "working", "defective", "durable", "poor", "excellent"));
        
        keywordMap.put("Technical", Arrays.asList("bug", "error", "crash", "glitch", "update", "login", "loading"));
        
        keywordMap.put("Order", Arrays.asList("order", "cancel", "return", "exchange"));
        
        keywordMap.put("Experience", Arrays.asList("feedback", "service", "usage", "interface", "navigation", "onboarding", "installation", "setup"));
        
        keywordMap.put("Sentiment", Arrays.asList("satisfied", "happy", "disappointed", "angry", "frustrated", "impressed", "annoyed", "grateful"));

    }

    public Map<String, List<String>> getKeywords() {
        return keywords;
    }
}
