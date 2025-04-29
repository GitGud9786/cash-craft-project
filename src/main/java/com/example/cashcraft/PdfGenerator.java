package com.example.cashcraft;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
//import com.itextpdf.layout.property.TextAlignment;
//import com.itextpdf.layout.property.UnitValue;


import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PdfGenerator {
//    private final ReportService reportService;
//
//    public PdfGenerator() throws Exception {
//        Connection conn = Makeconnection.makeconnection();
//        this.reportService = new ReportService(conn);
//    }
//
//    public void generateFullReport(String folderPath) throws Exception {
//        String today = new SimpleDateFormat("MMMM-yyyy").format(new Date());
//        String fileName = "MoneyReport-" + today + ".pdf";
//        String fullPath = folderPath + File.separator + fileName;
//
//        File reportsFolder = new File(folderPath);
//        if (!reportsFolder.exists()) {
//            reportsFolder.mkdirs();
//        }
//
//        PdfWriter writer = new PdfWriter(fullPath);
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document document = new Document(pdfDoc, PageSize.A4);
//        document.setMargins(20, 20, 20, 20);
//
//        Paragraph title = new Paragraph("💰 Money Management Statistical Report")
//                .setFontSize(24)
//                .setBold()
//                .setTextAlignment(TextAlignment.CENTER)
//                .setMarginBottom(20);
//        document.add(title);
//
//        document.add(new Paragraph("🔹 Key Statistics").setFontSize(18).setBold().setMarginBottom(10));
//
//        Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
//        table.addHeaderCell(new Cell().add(new Paragraph("Statistic")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));
//        table.addHeaderCell(new Cell().add(new Paragraph("Value")).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY));
//
//        double totalIncome = reportService.getTotalIncome();
//        double totalExpense = reportService.getTotalExpense();
//        double netBalance = reportService.getNetBalance();
//        double avgExpense = reportService.getAverageExpensePerTransaction();
//        double stdDevExpense = reportService.getStandardDeviationExpense();
//        double highestExpense = reportService.getHighestExpenseAmount();
//        String highestExpenseDesc = reportService.getHighestExpenseDescription();
//        Map<String, Double> categoryMap = reportService.getExpenseByCategory();
//        String mostFrequentCategory = categoryMap.entrySet().stream()
//                .max(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .orElse("N/A");
//
//        table.addCell(new Cell().add(new Paragraph("Total Income")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", totalIncome))));
//
//        table.addCell(new Cell().add(new Paragraph("Total Expense")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", totalExpense))));
//
//        table.addCell(new Cell().add(new Paragraph("Net Balance")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", netBalance))));
//
//        table.addCell(new Cell().add(new Paragraph("Average Expense per Transaction")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", avgExpense))));
//
//        table.addCell(new Cell().add(new Paragraph("Standard Deviation of Expenses")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", stdDevExpense))));
//
//        table.addCell(new Cell().add(new Paragraph("Highest Single Expense")));
//        table.addCell(new Cell().add(new Paragraph(String.format("$%.2f (%s)", highestExpense, highestExpenseDesc))));
//
//        table.addCell(new Cell().add(new Paragraph("Most Frequent Spending Category")));
//        table.addCell(new Cell().add(new Paragraph(mostFrequentCategory)));
//
//        document.add(table);
//
//        document.add(new Paragraph("\n"));
//
//        document.add(new Paragraph("🔹 Expense Distribution by Category").setFontSize(18).setBold().setMarginBottom(10));
//
//        // Placeholder for pie chart and bar chart logic
//        // Ensure ChartFactory is implemented correctly
//
//        document.add(new Paragraph("\n"));
//        document.add(new LineSeparator(new com.itextpdf.layout.element.LineSeparator()));
//
//        String reportDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        Paragraph footer = new Paragraph("📅 Report Generated on: " + reportDate)
//                .setFontSize(10)
//                .setTextAlignment(TextAlignment.RIGHT);
//        document.add(footer);
//
//        document.close();
//        openPdf(fullPath);
//    }
//
//    private void openPdf(String path) {
//        try {
//            if (java.awt.Desktop.isDesktopSupported()) {
//                java.awt.Desktop.getDesktop().open(new File(path));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}