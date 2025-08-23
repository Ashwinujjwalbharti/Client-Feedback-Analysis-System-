package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private ReportServiceImpl service;

    private Feedback fb(String category, String sentiment, LocalDateTime submittedAt) {
        Feedback f = new Feedback();
        f.setCategory(category);
        f.setSentiment(sentiment);
        f.setSubmittedAt(submittedAt);
        return f;
    }

    private String extractTextFromPdf(byte[] bytes) throws Exception {
        try (PdfDocument pdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(bytes)))) {
            StringBuilder sb = new StringBuilder();
            int pages = pdf.getNumberOfPages();
            for (int i = 1; i <= pages; i++) {
                sb.append(PdfTextExtractor.getTextFromPage(pdf.getPage(i)));
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    @Test
    @DisplayName("getSentimentTrends: aggregates counts per sentiment, ignoring nulls")
    void getSentimentTrends_aggregatesCounts() {
        List<Feedback> data = List.of(
                fb("support", "positive", LocalDateTime.now()),
                fb("billing", "negative", LocalDateTime.now()),
                fb("product", "positive", LocalDateTime.now()),
                fb("sales", null, LocalDateTime.now())
        );

        Map<String, Long> result = service.getSentimentTrends(data);

        assertEquals(2L, result.get("positive"));
        assertEquals(1L, result.get("negative"));
        assertFalse(result.containsKey(null));
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getRecurringIssues: current behavior with multi-category may undercount due to logic bug")
    void getRecurringIssues_currentBehavior_buggyCounting() {

        List<Feedback> data = List.of(
                fb("support", "positive", LocalDateTime.now()),
                fb("support,product", "positive", LocalDateTime.now()),
                fb("product", "negative", LocalDateTime.now())
        );

        Map<String, Long> result = service.getRecurringIssues(data);

        assertEquals(1L, result.get("support"));
        assertEquals(2L, result.get("product"));
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("generateReport: aggregates recurring issues and sentiment trends from repository data")
    void generateReport_aggregatesMaps() {
        int weeks = 2;
        List<Feedback> data = List.of(
                fb("support", "positive", LocalDateTime.now().minusDays(5)),
                fb("billing", "negative", LocalDateTime.now().minusDays(3)),
                fb("support", "positive", LocalDateTime.now().minusDays(1))
        );
        when(repository.findBySubmittedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(data);

        Map<String, Object> report = service.generateReport(weeks);

        assertNotNull(report);
        assertTrue(report.containsKey("recurringIssues"));
        assertTrue(report.containsKey("sentimentTrends"));

        Map<String, Long> issues = (Map<String, Long>) report.get("recurringIssues");
        Map<String, Long> sentiments = (Map<String, Long>) report.get("sentimentTrends");

        assertEquals(2L, issues.get("support"));
        assertEquals(1L, issues.get("billing"));
        assertEquals(2, issues.size());

        assertEquals(2L, sentiments.get("positive"));
        assertEquals(1L, sentiments.get("negative"));
        assertEquals(2, sentiments.size());

        verify(repository).findBySubmittedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("generateReport: calls repository with [now - weeks, now] range (approx)")
    void generateReport_callsRepositoryWithCorrectDateRange() {
        int weeks = 3;

        when(repository.findBySubmittedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        service.generateReport(weeks);

        ArgumentCaptor<LocalDateTime> startCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> endCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findBySubmittedAtBetween(startCaptor.capture(), endCaptor.capture());

        LocalDateTime capturedStart = startCaptor.getValue();
        LocalDateTime capturedEnd = endCaptor.getValue();

        LocalDateTime now = LocalDateTime.now();
        assertTrue(!capturedEnd.isBefore(now.minusSeconds(5)) && !capturedEnd.isAfter(now.plusSeconds(5)),
                "End should be ~now");

        long seconds = Math.abs(Duration.between(capturedStart, capturedEnd.minusWeeks(weeks)).getSeconds());
        assertTrue(seconds <= 5, "Start should be about " + weeks + " weeks before end");

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("generatePdfReport: creates non-empty PDF with sections and data")
    void generatePdfReport_containsHeadingsAndData() throws Exception {
        int weeks = 2;
        List<Feedback> data = List.of(
                fb("support", "positive", LocalDateTime.now().minusDays(5)),
                fb("billing", "negative", LocalDateTime.now().minusDays(3)),
                fb("support", "positive", LocalDateTime.now().minusDays(1))
        );
        when(repository.findBySubmittedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(data);

        byte[] pdfBytes = service.generatePdfReport(weeks);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        String text = extractTextFromPdf(pdfBytes);
        assertTrue(text.contains("Client Feedback Report - Last " + weeks + " Week(s)"));
        assertTrue(text.contains("Generated on:"));
        assertTrue(text.contains("Recurring Issues:"));
        assertTrue(text.contains("Sentiment Trends:"));

        assertTrue(text.contains("support"));
        assertTrue(text.contains("billing"));
        
        assertTrue(text.contains("2")); 
        assertTrue(text.contains("1")); 

        assertTrue(text.contains("positive"));
        assertTrue(text.contains("negative"));
    }

    @Test
    @DisplayName("generatePdfReport: shows 'No feedback data available' when repository returns empty list")
    void generatePdfReport_emptyData_showsNoDataMessage() throws Exception {
        int weeks = 1;
        when(repository.findBySubmittedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        byte[] pdfBytes = service.generatePdfReport(weeks);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        String text = extractTextFromPdf(pdfBytes);
        assertTrue(text.contains("No feedback data available for the selected period."));
    }
}
