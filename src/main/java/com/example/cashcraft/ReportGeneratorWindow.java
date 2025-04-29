package com.example.cashcraft;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ReportGeneratorWindow {

    public Scene createScene(Stage stage) {
        // Create UI components
        Label filePathLabel = new Label("File Path:");
        TextField filePathField = new TextField();
        Button browseButton = new Button("Browse");

        Label fileNameLabel = new Label("File Name:");
        TextField fileNameField = new TextField();

        Label monthLabel = new Label("Select Month:");
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        Label yearLabel = new Label("Enter Year:");
        TextField yearTextField = new TextField();

        Button generateButton = new Button("Generate Report");

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(filePathLabel, 0, 0);
        gridPane.add(filePathField, 1, 0);
        gridPane.add(browseButton, 2, 0);

        gridPane.add(fileNameLabel, 0, 1);
        gridPane.add(fileNameField, 1, 1);

        gridPane.add(monthLabel, 0, 2);
        gridPane.add(monthComboBox, 1, 2);

        gridPane.add(yearLabel, 0, 3);
        gridPane.add(yearTextField, 1, 3);

        gridPane.add(generateButton, 1, 4);

        // Browse button action
        browseButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                filePathField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        // Generate button action
        generateButton.setOnAction(event -> {
            String filePath = filePathField.getText();
            String fileName = fileNameField.getText();
            String selectedMonth = monthComboBox.getValue();
            String year = yearTextField.getText();

            if (filePath.isEmpty() || fileName.isEmpty() || selectedMonth == null || year.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                return;
            }

            int month = monthComboBox.getItems().indexOf(selectedMonth) + 1; // Convert month name to number
            try {
                int yearInt = Integer.parseInt(year); // Validate year input

                String fullFilePath = filePath + File.separator + fileName + ".pdf";

                ReportGenerator generator = new ReportGenerator();
                generator.generateReport(fullFilePath, selectedMonth + " " + year + " Report", month, yearInt);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Report generated successfully!");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Year must be a valid number!");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report: " + e.getMessage());
            }
        });

        return new Scene(gridPane, 400, 300);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}