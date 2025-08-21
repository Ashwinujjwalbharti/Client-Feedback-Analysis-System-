package org.godigit.ClientFeedbackAnalysisSystem.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FeedbackSentimentUpdate {
    private Long id;
    private String name;
    private String sentiment;
}
