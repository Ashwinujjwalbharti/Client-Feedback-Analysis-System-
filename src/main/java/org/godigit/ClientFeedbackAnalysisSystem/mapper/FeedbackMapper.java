package org.godigit.ClientFeedbackAnalysisSystem.mapper;

import org.godigit.ClientFeedbackAnalysisSystem.dto.FeedbackDto;
import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

public class FeedbackMapper {

    public static Feedback toEntity(FeedbackDto dto) {
        Feedback feedback = new Feedback();
        feedback.setClientName(dto.getName());
        feedback.setMessage(dto.getMessage());
        return feedback;
    }

    // Optional: if you want to convert back from entity to DTO
    public static FeedbackDto toDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();
        dto.setName(feedback.getClientName());
        dto.setMessage(feedback.getMessage());
        return dto;
    }
}
