package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilterServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private FilterServiceImpl service;

    private Feedback fb(String category, String sentiment, LocalDateTime submittedAt) {
        Feedback f = new Feedback();
        f.setCategory(category);
        f.setSentiment(sentiment);
        f.setSubmittedAt(submittedAt);
        return f;
    }

    @Test
    @DisplayName("filterByDate: returns feedbacks submitted on the exact date")
    void filterByDate_returnsOnlyMatchingDate() {
        LocalDate date = LocalDate.of(2025, 8, 10);

        List<Feedback> data = List.of(
            fb("support", "positive", date.atTime(10, 0)),
            fb("billing", "negative", date.atTime(23, 59)),
            fb("general", "neutral", date.plusDays(1).atTime(0, 1))
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.filterByDate(date);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getSubmittedAt().toLocalDate().equals(date)));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByDate: returns empty list when none match")
    void filterByDate_noMatchesReturnsEmpty() {
        LocalDate date = LocalDate.of(2025, 8, 12);

        List<Feedback> data = List.of(
            fb("support", "positive", date.minusDays(1).atTime(12, 0)),
            fb("billing", "negative", date.minusDays(2).atTime(8, 30))
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.filterByDate(date);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByDate: returns empty list when repository is empty")
    void filterByDate_emptyRepository() {
        when(repository.findAll()).thenReturn(List.of());

        var result = service.filterByDate(LocalDate.of(2025, 1, 1));

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByDate: null date -> equals(null) is false, so result is empty")
    void filterByDate_nullDate_returnsEmpty() {
        List<Feedback> data = List.of(
            fb("support", "positive", LocalDateTime.of(2025, 8, 10, 12, 0))
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.filterByDate(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getBySentiment: case-insensitive equality, ignores null sentiments")
    void getBySentiment_caseInsensitiveAndSkipsNulls() {
        String sentiment = "Positive";

        List<Feedback> data = List.of(
            fb("support", "POSITIVE", LocalDateTime.now()),
            fb("billing", "positive", LocalDateTime.now()),
            fb("general", "negative", LocalDateTime.now()),
            fb("sales", null, LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.getBySentiment(sentiment);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> "positive".equalsIgnoreCase(f.getSentiment())));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getBySentiment: returns empty when no sentiments match")
    void getBySentiment_noMatches() {
        List<Feedback> data = List.of(
            fb("support", "neutral", LocalDateTime.now()),
            fb("billing", "negative", LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.getBySentiment("positive");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getBySentiment: returns empty list when repository empty")
    void getBySentiment_emptyRepository() {
        when(repository.findAll()).thenReturn(List.of());

        var result = service.getBySentiment("neutral");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByCategory: case-insensitive substring match, ignores null categories")
    void filterByCategory_caseInsensitiveContains() {
        String categoryFilter = "port"; 

        List<Feedback> data = List.of(
            fb("Support", "positive", LocalDateTime.now()),
            fb("Customer Support", "neutral", LocalDateTime.now()),
            fb("Billing", "negative", LocalDateTime.now()),
            fb(null, "positive", LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.filterByCategory(categoryFilter);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(
            f -> f.getCategory().toLowerCase().contains(categoryFilter.toLowerCase())
        ));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByCategory: returns empty list when no category contains substring")
    void filterByCategory_noMatches() {
        String categoryFilter = "tech";

        List<Feedback> data = List.of(
            fb("Support", "positive", LocalDateTime.now()),
            fb("Billing", "negative", LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(data);

        var result = service.filterByCategory(categoryFilter);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByCategory: returns empty list when repository is empty")
    void filterByCategory_emptyRepository() {
        when(repository.findAll()).thenReturn(List.of());

        var result = service.filterByCategory("billing");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("filterByCategory: null filter -> throws NullPointerException (current behavior)")
    void filterByCategory_nullFilter_throwsNPE() {
        when(repository.findAll()).thenReturn(List.of(
            fb("Support", "positive", LocalDateTime.now())
        ));

        assertThrows(NullPointerException.class, () -> service.filterByCategory(null));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }
}
