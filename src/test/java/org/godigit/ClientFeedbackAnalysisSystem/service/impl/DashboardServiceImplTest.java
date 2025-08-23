package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private DashboardServiceImpl service;

    private Feedback feedback(String client, String category) {
        Feedback f = new Feedback();
        f.setClientName(client);
        f.setCategory(category);
        return f;
    }

    @Test
    @DisplayName("getPaginatedFeedbacks: returns paginated feedbacks")
    void getPaginatedFeedbacks_returnsPage() {
        int page = 0;
        int size = 2;
        PageRequest pageable = PageRequest.of(page, size);

        List<Feedback> feedbackList = List.of(
                feedback("Acme", "support"),
                feedback("Globex", "billing"));
        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, pageable, feedbackList.size());

        when(repository.findAll(pageable)).thenReturn(feedbackPage);

        Page<Feedback> result = service.getPaginatedFeedbacks(page, size);

        assertEquals(2, result.getContent().size());
        assertEquals("Acme", result.getContent().get(0).getClientName());
        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("getPaginatedFeedbacksByCategory: returns paginated feedbacks filtered by category")
    void getPaginatedFeedbacksByCategory_returnsFilteredPage() {
        int page = 0;
        int size = 2;
        String category = "support";
        PageRequest pageable = PageRequest.of(page, size);

        List<Feedback> feedbackList = List.of(
                feedback("Acme", "support"),
                feedback("Globex", "support"));
        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, pageable, feedbackList.size());

        when(repository.findByCategory(category, pageable)).thenReturn(feedbackPage);

        Page<Feedback> result = service.getPaginatedFeedbacksByCategory(category, page, size);

        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().allMatch(f -> f.getCategory().equals(category)));
        verify(repository).findByCategory(category, pageable);
        verifyNoMoreInteractions(repository);
    }
}
