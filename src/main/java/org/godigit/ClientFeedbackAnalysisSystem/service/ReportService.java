package org.godigit.ClientFeedbackAnalysisSystem.service;

import org.godigit.ClientFeedbackAnalysisSystem.models.Feedback;

import java.util.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;


public interface ReportService {
    public Map<String, Object> generateReport(int weeks);
    public Map<String, Long> getRecurringIssues(List<Feedback> feedbacks);
    public Map<String, Long> getSentimentTrends(List<Feedback> feedbacks);
    public byte[] generatePdfReport(int weeks);
}   