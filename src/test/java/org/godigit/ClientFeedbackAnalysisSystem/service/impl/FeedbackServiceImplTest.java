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
class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private FeedbackServiceImpl service;

    private Feedback feedback(String client, String category, String sentiment) {
        Feedback f = new Feedback();
        // Adjust these setters if your model is different (e.g., Lombok @Builder, constructor, etc.)
        f.setClientName(client);
        f.setCategory(category);
        f.setSentiment(sentiment);
        return f;
    }

    @Test
    @DisplayName("saveFeedback: delegates to repository.save and returns saved entity")
    void saveFeedback_returnsSavedEntity() {
        Feedback input = feedback("Acme", "support", "positive");
        Feedback saved = feedback("Acme", "support", "positive");
        // If your entity sets ID in DB, you can simulate: saved.setId(1L);

        when(repository.save(input)).thenReturn(saved);

        Feedback result = service.saveFeedback(input);

        assertSame(saved, result);
        verify(repository, times(1)).save(input);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getAllFeedback: returns all feedbacks from repository")
    void getAllFeedback_returnsList() {
        List<Feedback> data = List.of(
                feedback("Acme", "support", "positive"),
                feedback("Globex", "billing", "negative")
        );
        when(repository.findAll()).thenReturn(data);

        List<Feedback> result = service.getAllFeedback();

        assertEquals(2, result.size());
        assertEquals("Acme", result.get(0).getClientName());
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getFeedbackByClientName: filters by clientName (equalsIgnoreCase)")
    void getFeedbackByClientName_filtersIgnoreCase() {
        List<Feedback> data = List.of(
                feedback("Acme", "support", "positive"),
                feedback("ACME", "billing", "neutral"),
                feedback("Globex", "support", "negative")
        );
        when(repository.findAll()).thenReturn(data);

        List<Feedback> result = service.getFeedbackByClientName("acme");

        assertEquals(2, result.size(), "Should match both 'Acme' and 'ACME'");
        assertTrue(result.stream().allMatch(f -> f.getClientName().equalsIgnoreCase("acme")));
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("deleteClientFeedback: when none found, returns message and does not delete")
    void deleteClientFeedback_whenNone_returnsMessage() {
        List<Feedback> data = List.of(
                feedback("Globex", "support", "negative")
        );
        when(repository.findAll()).thenReturn(data);

        String msg = service.deleteClientFeedback("Acme");

        assertEquals("There are no feedbacks.", msg);
        verify(repository, times(1)).findAll();
        verify(repository, never()).deleteAll(anyList());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("deleteClientFeedback: when found, deletes matching and returns success message")
    void deleteClientFeedback_whenFound_deletesAndReturnsMessage() {
        Feedback f1 = feedback("Acme", "support", "positive");
        Feedback f2 = feedback("ACME", "billing", "neutral");
        Feedback f3 = feedback("Globex", "billing", "negative");
        when(repository.findAll()).thenReturn(List.of(f1, f2, f3));

        String msg = service.deleteClientFeedback("acme");

        assertEquals("Client Feedbacks deleted successfully.", msg);

        ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).findAll();
        verify(repository).deleteAll(captor.capture());
        List<Feedback> deleted = captor.getValue();
        assertEquals(2, deleted.size());
        assertTrue(deleted.containsAll(List.of(f1, f2)));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("deleteFeedbacks: deletes all feedbacks and returns message")
    void deleteFeedbacks_deletesAllAndReturnsMessage() {
        String msg = service.deleteFeedbacks();

        assertEquals("All the client feedbacks deleted successully.", msg);
        verify(repository, times(1)).deleteAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("deleteFeedbackByCategory: filters by contains(category.toLowerCase()) and deletes matched")
    void deleteFeedbackByCategory_filtersContainsLowercaseParamOnly() {
        Feedback f1 = feedback("Acme", "customer support", "positive"); // contains "support"
        Feedback f2 = feedback("Globex", "Support", "neutral");         // DOES NOT match because case-sensitive 'contains'
        Feedback f3 = feedback("Soylent", "billing", "negative");       // no match
        when(repository.findAll()).thenReturn(List.of(f1, f2, f3));

        String msg = service.deleteFeedbackByCategory("support");

        assertEquals("Category based client feedbacks have been successfully deleted.", msg);

        ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).findAll();
        verify(repository).deleteAll(captor.capture());
        List<Feedback> deleted = captor.getValue();
        assertEquals(1, deleted.size(), "Only lowercase 'customer support' contains 'support'");
        assertTrue(deleted.contains(f1));
        assertFalse(deleted.contains(f2));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("deleteFeedbackBySentiment: filters by equalsIgnoreCase(sentiment) and deletes matched")
    void deleteFeedbackBySentiment_equalsIgnoreCase() {
        Feedback f1 = feedback("Acme", "support", "Positive");
        Feedback f2 = feedback("Globex", "billing", "positive");
        Feedback f3 = feedback("Soylent", "support", "negative");
        when(repository.findAll()).thenReturn(List.of(f1, f2, f3));

        String msg = service.deleteFeedbackBySentiment("POSITIVE");

        assertEquals("Sentiment based client feedbacks have been successfully deleted.", msg);

        ArgumentCaptor<List<Feedback>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).findAll();
        verify(repository).deleteAll(captor.capture());
        List<Feedback> deleted = captor.getValue();
        assertEquals(2, deleted.size());
        assertTrue(deleted.containsAll(List.of(f1, f2)));
        assertFalse(deleted.contains(f3));
        verifyNoMoreInteractions(repository);
    }
}
