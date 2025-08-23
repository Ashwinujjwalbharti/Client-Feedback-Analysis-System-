package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeywordCategorizationServiceImplTest {

    @Mock
    private KeywordCategorizer keywordCategorizer;

    @InjectMocks
    private KeywordCategorizationServiceImpl service;

    private Map<String, List<String>> keywordMap() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        map.put("support", List.of("help", "support", "assist"));
        map.put("billing", List.of("invoice", "billing", "charge"));
        map.put("product", List.of("bug", "feature", "crash"));
        return map;
    }

    @BeforeEach
    void setUpKeywords() {
        when(keywordCategorizer.getKeywords()).thenReturn(keywordMap());
    }

    @Test
    @DisplayName("categorizeFeedback: returns single category when one category matches")
    void categorizeFeedback_singleCategory() {
        String msg = "I need help with my account.";

        String result = service.categorizeFeedback(msg);

        assertEquals("support", result);
        verify(keywordCategorizer, atLeastOnce()).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: returns comma-separated categories when multiple match (order preserved)")
    void categorizeFeedback_multipleCategories() {
        String msg = "The invoice has a wrong charge and the app tends to crash.";

        String result = service.categorizeFeedback(msg);

        assertEquals("billing,product", result);
    }

    @Test
    @DisplayName("categorizeFeedback: case-insensitive match for both message and keywords")
    void categorizeFeedback_caseInsensitive() {
        String msg = "Please SuPpOrT me, I need HeLP urgently.";

        String result = service.categorizeFeedback(msg);

        assertEquals("support", result);
    }

    @Test
    @DisplayName("categorizeFeedback: multiple keywords of the same category produce only one category (distinct)")
    void categorizeFeedback_noDuplicatesPerCategory() {
        String msg = "Need help and support and some assistance please!";

        String result = service.categorizeFeedback(msg);

        assertEquals("support", result);
        assertFalse(result.contains("support,support"));
    }

    @Test
    @DisplayName("categorizeFeedback: returns empty string when there are no matches")
    void categorizeFeedback_noMatchReturnsEmpty() {
        String msg = "Just saying hello world with no relevant terms.";

        String result = service.categorizeFeedback(msg);

        assertEquals("", result);
    }
}
