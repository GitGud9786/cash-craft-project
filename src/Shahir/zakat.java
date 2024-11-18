ZakwalletListView.setItems(observableList);
        ZakwalletListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
String walName;
ResultSet tot_Amount;
walName = newValue.getName();
                nameField.setText(newValue.getName());
        descField.setText(newValue.getDescription());
        try (Connection connection = Makeconnection.makeconnection()) {
PreparedStatement preparedStatement = connection.prepareStatement("select current_balance from wallet_balance_view where wallet_name = ?");
                    preparedStatement.setString(1, walName);
tot_Amount = preparedStatement.executeQuery();
int val = tot_Amount.getInt("current_balance");
                    zakField.setText(String.valueOf(val * .025));
        } catch (SQLException e) {
        throw new RuntimeException(e);
                }
                        }
                        });