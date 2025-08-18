package org.godigit.ClientFeedbackAnalysisSystem.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Data
@AllArgsConstructor
public class FeedbackDto {
    private String name;
    private String message;
    private String category;
}
