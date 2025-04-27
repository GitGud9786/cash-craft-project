package com.example.cashcraft;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportGenerator {

    public void generateReport(String filePath, String reportTitle) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add title
        document.add(new Paragraph(reportTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        document.add(new Paragraph("Generated on: " + new java.util.Date(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
        document.add(Chunk.NEWLINE);

        // Add Overview Section
        document.add(new Paragraph("Overview:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable overviewTable = createOverviewTable();
        document.add(overviewTable);
        document.add(Chunk.NEWLINE);

        // Add Expense Section
        document.add(new Paragraph("Expenses:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable expenseTable = createExpenseTable();
        document.add(expenseTable);
        document.add(Chunk.NEWLINE);

        // Add Income Section
        document.add(new Paragraph("Incomes:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable incomeTable = createIncomeTable();
        document.add(incomeTable);
        document.add(Chunk.NEWLINE);

        // Add Transfer Section
        document.add(new Paragraph("Transfers:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable transferTable = createTransferTable();
        document.add(transferTable);

        document.close();
    }

    private PdfPTable createExpenseTable() throws SQLException {
        PdfPTable table = new PdfPTable(6); // Adjust column count as needed
        table.setWidthPercentage(100);

        // Add table headers
        table.addCell("Transaction ID");
        table.addCell("Amount");
        table.addCell("Description");
        table.addCell("Category");
        table.addCell("Date");
        table.addCell("Wallet");

        // Fetch expense data from the database
        String query = "SELECT transaction_id, amount, \"desc\", category, \"date\", wallet FROM expense";
        try (Connection conn = Makeconnection.makeconnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                table.addCell(rs.getString("transaction_id"));
                table.addCell(String.valueOf(rs.getDouble("amount")));
                table.addCell(rs.getString("desc"));
                table.addCell(rs.getString("category"));
                table.addCell(rs.getString("date"));
                table.addCell(rs.getString("wallet"));
            }
        }

        return table;
    }

    private PdfPTable createOverviewTable() throws SQLException {
        PdfPTable table = new PdfPTable(5); // 5 columns for the overview
        table.setWidthPercentage(100);

        // Add table headers
        table.addCell("Wallet Name");
        table.addCell("Total Income");
        table.addCell("Total Expense");
        table.addCell("Total Outgoing Transfer");
        table.addCell("Current Balance");

        // Fetch data from the wallet_balance_view
        String query = "SELECT wallet_name, total_income, total_expense, total_outgoing_transfer, current_balance FROM wallet_balance_view";
        try (Connection conn = Makeconnection.makeconnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                table.addCell(rs.getString("wallet_name"));
                table.addCell(String.valueOf(rs.getDouble("total_income")));
                table.addCell(String.valueOf(rs.getDouble("total_expense")));
                table.addCell(String.valueOf(rs.getDouble("total_outgoing_transfer")));
                table.addCell(String.valueOf(rs.getDouble("current_balance")));
            }
        }

        return table;
    }

    private PdfPTable createIncomeTable() throws SQLException {
        PdfPTable table = new PdfPTable(6); // Adjust column count as needed
        table.setWidthPercentage(100);

        // Add table headers
        table.addCell("Income ID");
        table.addCell("Amount");
        table.addCell("Description");
        table.addCell("Category");
        table.addCell("Date");
        table.addCell("Wallet");

        // Fetch income data from the database
        String query = "SELECT income_id, amount, \"desc\", category, \"date\", wallet FROM income";
        try (Connection conn = Makeconnection.makeconnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                table.addCell(rs.getString("income_id"));
                table.addCell(String.valueOf(rs.getDouble("amount")));
                table.addCell(rs.getString("desc"));
                table.addCell(rs.getString("category"));
                table.addCell(rs.getString("date"));
                table.addCell(rs.getString("wallet"));
            }
        }

        return table;
    }

    private PdfPTable createTransferTable() throws SQLException {
        PdfPTable table = new PdfPTable(7); // Adjust column count as needed
        table.setWidthPercentage(100);

        // Add table headers
        table.addCell("Transfer ID");
        table.addCell("Amount");
        table.addCell("Description");
        table.addCell("Category");
        table.addCell("Date");
        table.addCell("From Wallet");
        table.addCell("To Wallet");

        // Fetch transfer data from the database
        String query = "SELECT transfer_id, amount, \"desc\", category, \"date\", from_wallet, to_wallet FROM transfer";
        try (Connection conn = Makeconnection.makeconnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                table.addCell(rs.getString("transfer_id"));
                table.addCell(String.valueOf(rs.getDouble("amount")));
                table.addCell(rs.getString("desc"));
                table.addCell(rs.getString("category"));
                table.addCell(rs.getString("date"));
                table.addCell(rs.getString("from_wallet"));
                table.addCell(rs.getString("to_wallet"));
            }
        }

        return table;
    }
}