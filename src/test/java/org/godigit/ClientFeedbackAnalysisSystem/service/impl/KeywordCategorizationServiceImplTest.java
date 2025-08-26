package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.utils.KeywordCategorizer;
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

    private Map<String, List<String>> keywordsMap(Object... pairs) {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            String key = (String) pairs[i];
            List<String> value = (List<String>) pairs[i + 1];
            map.put(key, value);
        }
        return map;
    }

    @Test
    @DisplayName("categorizeFeedback: returns empty string for null message")
    void categorizeFeedback_nullMessage_returnsEmpty() {
        String result = service.categorizeFeedback(null);
        assertEquals("", result);

        verifyNoInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: returns empty string for blank message")
    void categorizeFeedback_blankMessage_returnsEmpty() {
        String result1 = service.categorizeFeedback("");
        String result2 = service.categorizeFeedback("   ");
        assertEquals("", result1);
        assertEquals("", result2);

        verifyNoInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: returns empty string when no keywords configured")
    void categorizeFeedback_emptyKeywordMap_returnsEmpty() {
        when(keywordCategorizer.getKeywords()).thenReturn(Map.of());

        String result = service.categorizeFeedback("payment failed due to gateway error");
        assertEquals("", result);

        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: returns empty string when no keywords match the message")
    void categorizeFeedback_noKeywordMatches_returnsEmpty() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Billing", List.of("invoice", "payment"),
                "Support", List.of("help", "issue")
            )
        );

        String result = service.categorizeFeedback("Design looks modern and clean");
        assertEquals("", result);

        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: matches a single category when any of its keywords appear")
    void categorizeFeedback_singleCategory_singleMatch() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Billing", List.of("invoice", "payment"),
                "Support", List.of("help", "issue")
            )
        );

        String result = service.categorizeFeedback("My payment failed at checkout");
        assertEquals("Billing", result);

        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: distinct category even if multiple keywords from same category match")
    void categorizeFeedback_singleCategory_multipleKeywords_distinct() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Billing", List.of("invoice", "payment", "gateway")
            )
        );

        String result = service.categorizeFeedback("Invoice not generated and payment gateway shows error");
        assertEquals("Billing", result, "Category should appear once due to distinct()");
        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: returns multiple categories joined by comma in map insertion order")
    void categorizeFeedback_multipleCategories_orderedByInsertion() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Billing", List.of("invoice", "payment"),
                "Support", List.of("help", "issue"),
                "UX", List.of("design", "ui")
            )
        );

        String msg = "I need help with payment; the UI also looks confusing.";
        String result = service.categorizeFeedback(msg);

        assertEquals("Billing,Support,UX", result);
        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: matching is case-insensitive on both message and keywords")
    void categorizeFeedback_caseInsensitive() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Support", List.of("HeLp", "ISSUE"),
                "Billing", List.of("PaYMenT")
            )
        );

        String result = service.categorizeFeedback("Need HELP with PAYMENT asap due to an Issue");
        assertEquals("Support,Billing", result);

        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }

    @Test
    @DisplayName("categorizeFeedback: uses substring containment (not whole-word match)")
    void categorizeFeedback_substringContainment() {
        when(keywordCategorizer.getKeywords()).thenReturn(
            keywordsMap(
                "Billing", List.of("pay"),
                "Support", List.of("port")
            )
        );

        String result = service.categorizeFeedback("Support team resolved my payment problem");
        assertEquals("Billing,Support", result);

        verify(keywordCategorizer).getKeywords();
        verifyNoMoreInteractions(keywordCategorizer);
    }
}
