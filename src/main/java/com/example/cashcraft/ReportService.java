package com.example.cashcraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ReportService {
    private final Connection conn;

    public ReportService(Connection conn) {
        this.conn = conn;
    }

    public double getTotalIncome() throws Exception {
        String sql = "SELECT SUM(amount) FROM Income";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.getDouble(1);
        }
    }

    public double getTotalExpense() throws Exception {
        String sql = "SELECT SUM(amount) FROM expense";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.getDouble(1);
        }
    }

    public double getNetBalance() throws Exception {
        return getTotalIncome() - getTotalExpense();
    }

    public double getAverageExpensePerTransaction() throws Exception {
        String sql = "SELECT AVG(amount) FROM expense";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.getDouble(1);
        }
    }

    public double getHighestExpenseAmount() throws Exception {
        String sql = "SELECT MAX(amount) FROM expense";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.getDouble(1);
        }
    }

    public String getHighestExpenseDescription() throws Exception {
        String sql = "SELECT desc FROM expense ORDER BY amount DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return "N/A";
            }
        }
    }

    public double getStandardDeviationExpense() throws Exception {
        String sql = "SELECT STDDEV_SAMP(amount) FROM expense"; // SQLite doesn't support STDDEV_SAMP
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.getDouble(1);
        } catch (Exception e) {
            // Workaround: calculate manually if SQLite
            return calculateStdDevManually();
        }
    }

    private double calculateStdDevManually() throws Exception {
        double mean = getAverageExpensePerTransaction();
        String sql = "SELECT amount FROM expense";
        double sumSqDiff = 0;
        int count = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                double amount = rs.getDouble(1);
                sumSqDiff += (amount - mean) * (amount - mean);
                count++;
            }
        }
        return (count > 1) ? Math.sqrt(sumSqDiff / (count - 1)) : 0;
    }

    public Map<String, Double> getExpenseByCategory() throws Exception {
        String sql = "SELECT c.category_name, SUM(e.amount) " +
                "FROM expense e JOIN category c ON e.category = c.category_id " +
                "GROUP BY c.category_name";
        Map<String, Double> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getDouble(2));
            }
        }
        return map;
    }

    public Map<String, Double> getMonthlyExpenses() throws Exception {
        String sql = "SELECT strftime('%Y-%m', date) AS month, SUM(amount) " +
                "FROM expense GROUP BY month ORDER BY month";
        Map<String, Double> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getDouble(2));
            }
        }
        return map;
    }
}
