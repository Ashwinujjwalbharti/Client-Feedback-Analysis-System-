package org.godigit.ClientFeedbackAnalysisSystem.service.impl;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;
import org.godigit.ClientFeedbackAnalysisSystem.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    void testGetPaginatedFeedbacks() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Feedback> feedbackList = List.of(new Feedback(), new Feedback());
        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, pageable, feedbackList.size());

        when(repository.findAll(pageable)).thenReturn(feedbackPage);

        Page<Feedback> result = service.getPaginatedFeedbacks(0, 2);

        assertEquals(2, result.getContent().size());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testGetPaginatedFeedbacksByCategory() {
        String category = "UI";
        Pageable pageable = PageRequest.of(1, 3);
        List<Feedback> feedbackList = List.of(new Feedback(), new Feedback(), new Feedback());
        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, pageable, feedbackList.size());

        when(repository.findByCategory(category, pageable)).thenReturn(feedbackPage);

        Page<Feedback> result = service.getPaginatedFeedbacksByCategory(category, 1, 3);

        assertEquals(3, result.getContent().size());
        verify(repository, times(1)).findByCategory(category, pageable);
    }

    @Test
    void testGetPaginatedFeedbacksBySentiment() {
        String sentiment = "Positive";
        Pageable pageable = PageRequest.of(0, 1);
        List<Feedback> feedbackList = List.of(new Feedback());
        Page<Feedback> feedbackPage = new PageImpl<>(feedbackList, pageable, feedbackList.size());

        when(repository.findBySentiment(sentiment, pageable)).thenReturn(feedbackPage);

        Page<Feedback> result = service.getPaginatedFeedbacksBySentiment(sentiment, 0, 1);

        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findBySentiment(sentiment, pageable);
    }
}
