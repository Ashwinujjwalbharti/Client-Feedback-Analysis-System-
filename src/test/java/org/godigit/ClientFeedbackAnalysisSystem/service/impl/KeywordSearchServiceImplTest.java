package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeywordSearchServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private KeywordSearchServiceImpl service;

    private Feedback fb(String category, String message, String clientName, String sentiment) {
        Feedback f = new Feedback();
        f.setCategory(category);
        f.setMessage(message);
        f.setClientName(clientName);
        f.setSentiment(sentiment);
        return f;
    }

    @Test
    @DisplayName("searchByKeyword: matches in category (case-insensitive)")
    void searchByKeyword_matchesCategory() {
        var data = List.of(
            fb("Billing Issues", null, null, null),
            fb("Support", null, null, null),
            fb(null, "Message unrelated", null, null)
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("billing");

        assertEquals(1, result.size());
        assertEquals("Billing Issues", result.get(0).getCategory());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: matches in message (case-insensitive)")
    void searchByKeyword_matchesMessage() {
        var data = List.of(
            fb(null, "Facing LOGIN problem", null, null),
            fb(null, "no relevant text", null, null)
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("login");

        assertEquals(1, result.size());
        assertEquals("Facing LOGIN problem", result.get(0).getMessage());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: matches in clientName (case-insensitive)")
    void searchByKeyword_matchesClientName() {
        var data = List.of(
            fb(null, null, "Acme Corp", null),
            fb(null, null, "Globex", null)
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("acme");

        assertEquals(1, result.size());
        assertEquals("Acme Corp", result.get(0).getClientName());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: matches in sentiment (case-insensitive)")
    void searchByKeyword_matchesSentiment() {
        var data = List.of(
            fb(null, null, null, "NEGATIVE"),
            fb(null, null, null, "neutral")
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("negative");

        assertEquals(1, result.size());
        assertEquals("NEGATIVE", result.get(0).getSentiment());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: case-insensitive and substring containment")
    void searchByKeyword_caseInsensitiveSubstring() {
        var data = List.of(
            fb("Billing", "Payment failed at checkout", "Contoso", "positive"),
            fb("Support", "Need HELP urgently", "Northwind", "neutral"),
            fb("UX", "UI is confusing", "AdventureWorks", "negative")
        );
        when(repository.findAll()).thenReturn(data);

        var resultPay = service.searchByKeyword("PaY");
        var resultHelp = service.searchByKeyword("help");

        assertEquals(1, resultPay.size());
        assertEquals("Payment failed at checkout", resultPay.get(0).getMessage());

        assertEquals(1, resultHelp.size());
        assertEquals("Support", resultHelp.get(0).getCategory());

        verify(repository, times(2)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: handles null fields in feedback gracefully")
    void searchByKeyword_nullFieldsInFeedback() {
        var data = List.of(
            fb(null, null, null, null),
            fb("Billing", null, null, null),
            fb(null, "Please help with invoice", null, null),
            fb(null, null, "Acme", null),
            fb(null, null, null, "positive")
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("help");

        assertEquals(1, result.size());
        assertEquals("Please help with invoice", result.get(0).getMessage());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: returns empty list when no matches")
    void searchByKeyword_noMatches() {
        var data = List.of(
            fb("Billing", "Payment failed", "Acme", "negative"),
            fb("Support", "Need assistance", "Globex", "neutral")
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword("unrelated-keyword");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: returns empty list when repository is empty")
    void searchByKeyword_emptyRepository() {
        when(repository.findAll()).thenReturn(List.of());

        var result = service.searchByKeyword("anything");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: blank keyword returns all feedbacks that have at least one non-null searchable field")
    void searchByKeyword_blankKeyword_returnsAllWithAnyField() {
        var data = List.of(
            fb(null, null, null, null),
            fb("Billing", null, null, null),
            fb(null, "Some message", null, null),
            fb(null, null, "Client X", null),
            fb(null, null, null, "positive")
        );
        when(repository.findAll()).thenReturn(data);

        var result = service.searchByKeyword(""); 
        
        assertEquals(4, result.size(), "All items with any non-null field should match");
        assertTrue(result.stream().noneMatch(f ->
            f.getCategory() == null &&
            f.getMessage() == null &&
            f.getClientName() == null &&
            f.getSentiment() == null
        ));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: null keyword -> throws NullPointerException (current behavior)")
    void searchByKeyword_nullKeyword_throwsNPE() {
        when(repository.findAll()).thenReturn(List.of(
            fb("Billing", "Payment failed", "Acme", "negative")
        ));

        assertThrows(NullPointerException.class, () -> service.searchByKeyword(null));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }
}
