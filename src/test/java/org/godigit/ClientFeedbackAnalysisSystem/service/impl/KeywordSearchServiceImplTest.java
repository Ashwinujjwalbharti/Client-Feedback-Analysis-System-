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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeywordSearchServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private KeywordSearchServiceImpl service;

    private Feedback feedback(String client, String message, String category, String sentiment) {
    Feedback f = new Feedback();
    f.setClientName(client);
    f.setMessage(message);
    f.setCategory(category);
    f.setSentiment(sentiment);
    return f;
}

    @Test
    @DisplayName("searchByKeyword: returns matching feedbacks from repository")
    void searchByKeyword_returnsMatches() {
        String keyword = "delay";
        Feedback f1 = feedback("Acme", "There was a delay in service", "support", "negative");
        Feedback f2 = feedback("Globex", "Delayed response from team", "billing", "neutral");

        when(repository.searchByKeyword(keyword)).thenReturn(List.of(f1, f2));

        List<Feedback> result = service.searchByKeyword(keyword);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f ->
            f.getMessage().toLowerCase().contains("delay")
        ));
        verify(repository, times(1)).searchByKeyword(keyword);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("searchByKeyword: returns empty list when no matches found")
    void searchByKeyword_returnsEmptyList() {
        String keyword = "nonexistent";
        when(repository.searchByKeyword(keyword)).thenReturn(List.of());

        List<Feedback> result = service.searchByKeyword(keyword);

        assertTrue(result.isEmpty());
        verify(repository).searchByKeyword(keyword);
        verifyNoMoreInteractions(repository);
    }
}

