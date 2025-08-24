package org.godigit.ClientFeedbackAnalysisSystem.utils;

import java.util.Properties;

import org.springframework.stereotype.Component;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

@Component
public class SentimentAnalyzer {

    private final StanfordCoreNLP stanfordCoreNLP;

    public SentimentAnalyzer() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize,ssplit,pos,parse,sentiment");
        this.stanfordCoreNLP = new StanfordCoreNLP(properties);
    }

    public String analyzeSentiment(String text) {
        Annotation annotation = new Annotation(text);
        stanfordCoreNLP.annotate(annotation);

        int totalScore = 0, sentenceCount = 0;

        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            switch (sentiment) {
                case "Very positive": totalScore += 2; break;
                case "Positive": totalScore += 1; break;
                case "Neutral": totalScore += 0; break;
                case "Negative": totalScore -= 1; break;
                case "Very negative": totalScore -= 2; break;
            }

            sentenceCount++;
        }

        double averageScore = (double) totalScore / sentenceCount;
        return (averageScore > 0) ? "Positive" : (averageScore < 0) ? "Negative" : "Neutral";
    }
}
