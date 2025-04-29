package com.example.cashcraft;
import com.itextpdf.text.pdf.draw.LineSeparator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.*;

public class ReportGenerator {

    public void generateReport(String filePath, String reportTitle, int month, int year) throws Exception {
        Document document = new Document(PageSize.A4, 36, 36, 54, 36); // custom margins
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph(reportTitle, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Overview Section
        addSectionTitle(document, "Overview");
        document.add(createOverviewTable());

        // Expense Section
        addSectionTitle(document, "Expenses");
        document.add(createExpenseTable(month, year));

        // Income Section
        addSectionTitle(document, "Incomes");
        document.add(createIncomeTable(month, year));

        // Transfer Section
        addSectionTitle(document, "Transfers");
        document.add(createTransferTable(month, year));

        document.close();
    }

    private void addSectionTitle(Document document, String title) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph sectionTitle = new Paragraph(title, sectionFont);
        sectionTitle.setSpacingBefore(10f);  // Reduced space before the section title
        sectionTitle.setSpacingAfter(0f);    // No space after the section title
        document.add(sectionTitle);

//        LineSeparator ls = new LineSeparator();
//        ls.setPercentage(100); // Full width line
//        document.add(new Chunk(ls));
//        // Remove Chunk.NEWLINE or reduce it
//        document.add(new Chunk(""));  // Empty chunk to control additional spacing
    }



    private PdfPTable createExpenseTable(int month, int year) throws SQLException {
        String query = "SELECT e.transaction_id, e.amount, e.\"desc\", c.category_name, e.\"date\", w.wallet_name " +
                "FROM expense e " +
                "JOIN category c ON e.category = c.category_id " +
                "JOIN wallet w ON e.wallet = w.wallet_id " +
                "WHERE strftime('%m', e.\"date\") = ? AND strftime('%Y', e.\"date\") = ?";
        try (Connection connection = Makeconnection.makeconnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.format("%02d", month)); // Format month as two digits
            statement.setString(2, String.valueOf(year)); // Convert year to string
            ResultSet resultSet = statement.executeQuery();
            return createTable(resultSet, new String[]{"Transaction ID", "Amount", "Description", "Category", "Date", "Wallet"}, 6);
        }
    }

    private PdfPTable createIncomeTable(int month, int year) throws SQLException {
        String query = "SELECT i.income_id, i.amount, i.\"desc\", COALESCE(c.category_name, 'N/A') AS category_name, i.\"date\", COALESCE(w.wallet_name, 'N/A') AS wallet_name " +
                "FROM income i " +
                "LEFT JOIN category c ON i.category = c.category_id " +
                "LEFT JOIN wallet w ON i.wallet = w.wallet_id " +
                "WHERE strftime('%m', i.\"date\") = ? AND strftime('%Y', i.\"date\") = ?";
        try (Connection connection = Makeconnection.makeconnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.format("%02d", month)); // Format month as two digits
            statement.setString(2, String.valueOf(year)); // Convert year to string
            ResultSet resultSet = statement.executeQuery();
            return createTable(resultSet, new String[]{"Income ID", "Amount", "Description", "Category", "Date", "Wallet"}, 6);
        }
    }

    private PdfPTable createTransferTable(int month, int year) throws SQLException {
        String query = "SELECT t.transfer_id, t.amount, t.\"desc\", COALESCE(c.category_name, 'N/A') AS category_name, t.\"date\", " +
                "COALESCE(fw.wallet_name, 'N/A') AS from_wallet, COALESCE(tw.wallet_name, 'N/A') AS to_wallet " +
                "FROM transfer t " +
                "LEFT JOIN category c ON t.category = c.category_id " +
                "LEFT JOIN wallet fw ON t.from_wallet = fw.wallet_id " +
                "LEFT JOIN wallet tw ON t.to_wallet = tw.wallet_id " +
                "WHERE strftime('%m', t.\"date\") = ? AND strftime('%Y', t.\"date\") = ?";
        try (Connection connection = Makeconnection.makeconnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.format("%02d", month)); // Format month as two digits
            statement.setString(2, String.valueOf(year)); // Convert year to string
            ResultSet resultSet = statement.executeQuery();
            return createTable(resultSet, new String[]{"Transfer ID", "Amount", "Description", "Category", "Date", "From Wallet", "To Wallet"}, 7);
        }
    }

    private PdfPTable createOverviewTable() throws SQLException {
        String query = "SELECT wallet_name, total_income, total_expense, total_outgoing_transfer, current_balance FROM wallet_balance_view";
        try (Connection connection = Makeconnection.makeconnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            return createTable(resultSet, new String[]{"Wallet Name", "Total Income", "Total Expense", "Total Outgoing Transfer", "Current Balance"}, 5);
        }
    }

//    private PdfPTable createIncomeTable() throws SQLException {
//        String query = "SELECT i.income_id, i.amount, i.\"desc\", COALESCE(c.category_name, 'N/A') AS category_name, i.\"date\", COALESCE(w.wallet_name, 'N/A') AS wallet_name " +
//                "FROM income i " +
//                "LEFT JOIN category c ON i.category = c.category_id " +
//                "LEFT JOIN wallet w ON i.wallet = w.wallet_id";
//        return createTable(query, new String[]{"Income ID", "Amount", "Description", "Category", "Date", "Wallet"}, 6);
//    }

//    private PdfPTable createTransferTable() throws SQLException {
//        String query = "SELECT t.transfer_id, t.amount, t.\"desc\", COALESCE(c.category_name, 'N/A') AS category_name, t.\"date\", " +
//                "COALESCE(fw.wallet_name, 'N/A') AS from_wallet, COALESCE(tw.wallet_name, 'N/A') AS to_wallet " +
//                "FROM transfer t " +
//                "LEFT JOIN category c ON t.category = c.category_id " +
//                "LEFT JOIN wallet fw ON t.from_wallet = fw.wallet_id " +
//                "LEFT JOIN wallet tw ON t.to_wallet = tw.wallet_id";
//        return createTable(query, new String[]{"Transfer ID", "Amount", "Description", "Category", "Date", "From Wallet", "To Wallet"}, 7);
//    }
    private PdfPTable createTable(ResultSet rs, String[] headers, int columns) throws SQLException {
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setHeaderRows(1);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        BaseColor headerBgColor = BaseColor.DARK_GRAY;
        BaseColor oddRowColor = new BaseColor(245, 245, 245); // light gray
        BaseColor evenRowColor = BaseColor.WHITE;

        // Add header cells
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(headerBgColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        // Fetch and add data rows
        int rowCount = 0;
        while (rs.next()) {
            BaseColor bgColor = (rowCount % 2 == 0) ? evenRowColor : oddRowColor;
            for (int i = 1; i <= columns; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(rs.getString(i)));
                cell.setBackgroundColor(bgColor);
                cell.setHorizontalAlignment(i == 2 ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT); // Amounts right-aligned
                cell.setPadding(6);
                table.addCell(cell);
            }
            rowCount++;
        }

        return table;
    }

    // Inner class for header/footer
    private static class HeaderFooterPageEvent extends PdfPageEventHelper {
        String title;

        public HeaderFooterPageEvent(String title) {
            this.title = title;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable header = new PdfPTable(1);
            try {
                header.setTotalWidth(520);
                header.setWidths(new int[]{24});
                header.setLockedWidth(true);
                header.getDefaultCell().setFixedHeight(30);
                header.getDefaultCell().setBorder(Rectangle.BOTTOM);
                header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                header.addCell(new Phrase(title, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GRAY)));
                header.writeSelectedRows(0, -1, 36, 820, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }

            PdfPTable footer = new PdfPTable(1);
            try {
                footer.setTotalWidth(520);
                footer.setWidths(new int[]{24});
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(30);
                footer.getDefaultCell().setBorder(Rectangle.TOP);
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                footer.addCell(new Phrase(String.format("Page %d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY)));
                footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }
}
