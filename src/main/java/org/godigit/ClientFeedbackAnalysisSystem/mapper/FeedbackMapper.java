package org.godigit.ClientFeedbackAnalysisSystem.mapper;

import java.time.LocalDateTime;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

public class FeedbackMapper {
    public static FeedbackDto toDto(Feedback feedback) {
        return new FeedbackDto(feedback.getClientName(), feedback.getMessage(), feedback.getEmoji());
    }

    public static Feedback toEntity(FeedbackDto feedbackDto) {
        return new Feedback(null, feedbackDto.getName(), feedbackDto.getMessage(), LocalDateTime.now(), null, null, feedbackDto.getEmoji(), null);
    }
}
