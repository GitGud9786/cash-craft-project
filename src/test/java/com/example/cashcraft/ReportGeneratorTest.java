package com.example.cashcraft;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportGeneratorTest {

    @Test
    public void testGenerateReport() {
        ReportGenerator generator = new ReportGenerator();
        String filePath = "test_report.pdf";
        String reportTitle = "Test Report";

        try {
            // Generate the report
            generator.generateReport(filePath, reportTitle);
            System.out.println("File path: " + new File(filePath).getAbsolutePath());


            // Verify the file is created
            File file = new File(filePath);
            assertTrue(file.exists(), "The PDF file should be created.");

            // Optionally, verify the file is not empty
            assertTrue(file.length() > 0, "The PDF file should not be empty.");

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false, "Exception occurred during report generation: " + e.getMessage());
        } finally {
            // Clean up the generated file
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}