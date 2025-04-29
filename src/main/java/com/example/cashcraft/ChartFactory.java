package com.example.cashcraft;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChartFactory {

    public static PieChart createExpensePieChart(Map<String, Double> data) {
        PieChart pieChart = new PieChart();
        data.forEach((key, value) -> {
            PieChart.Data slice = new PieChart.Data(key, value);
            pieChart.getData().add(slice);
        });
        pieChart.setTitle("Expenses by Category");
        return pieChart;
    }

    public static BarChart<String, Number> createMonthlyExpenseBarChart(Map<String, Double> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monthly Expenses");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        data.forEach((month, total) -> {
            series.getData().add(new XYChart.Data<>(month, total));
        });

        barChart.getData().add(series);
        return barChart;
    }

    public static File saveChartAsImage(Chart chart, String filename) throws IOException {
        WritableImage image = chart.snapshot(null, null);
        File file = new File(filename);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        return file;
    }
}
