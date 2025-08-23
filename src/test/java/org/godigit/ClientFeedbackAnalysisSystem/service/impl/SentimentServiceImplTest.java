package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.utils.SentimentAnalyzer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SentimentServiceImplTest {

    @Mock
    private SentimentAnalyzer sentimentAnalyzer;

    @InjectMocks
    private SentimentServiceImpl service;

    @Test
    @DisplayName("detectSentiment: returns 'Feedback is empty.' for null input and does not call analyzer")
    void detectSentiment_null_returnsEmptyMessage() {
        String result = service.detectSentiment(null);

        assertEquals("Feedback is empty.", result);
        verifyNoInteractions(sentimentAnalyzer);
    }

    @Test
    @DisplayName("detectSentiment: returns 'Feedback is empty.' for blank input and does not call analyzer")
    void detectSentiment_blank_returnsEmptyMessage() {
        String result1 = service.detectSentiment("");
        String result2 = service.detectSentiment("   \t  ");
        String result3 = service.detectSentiment("\n");

        assertEquals("Feedback is empty.", result1);
        assertEquals("Feedback is empty.", result2);
        assertEquals("Feedback is empty.", result3);
        verifyNoInteractions(sentimentAnalyzer);
    }

    @Test
    @DisplayName("detectSentiment: delegates to SentimentAnalyzer for non-blank content and returns its result")
    void detectSentiment_delegatesToAnalyzer() {
        String input = "Great job, team!";
        when(sentimentAnalyzer.analyzeSentiment(input)).thenReturn("Positive");

        String result = service.detectSentiment(input);

        assertEquals("Positive", result);
        verify(sentimentAnalyzer).analyzeSentiment(input);
        verifyNoMoreInteractions(sentimentAnalyzer);
    }

    @Test
    @DisplayName("detectSentiment: whitespace around content still delegates (uses original string)")
    void detectSentiment_nonBlankAfterTrim_stillDelegates() {
        String input = "   Good progress this sprint   ";
        when(sentimentAnalyzer.analyzeSentiment(input)).thenReturn("Positive");

        String result = service.detectSentiment(input);

        assertEquals("Positive", result);
        verify(sentimentAnalyzer).analyzeSentiment(input);
        verifyNoMoreInteractions(sentimentAnalyzer);
    }
}
