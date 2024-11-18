ZakwalletListView.setItems(observableList);
ZakwalletListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
    String walName;
    ResultSet tot_Amount;
    walName = newValue.getName();
                nameField.setText(newValue.getName());
        descField.setText(newValue.getDescription());
        try (Connection connection = Makeconnection.makeconnection()) {