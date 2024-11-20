public void on_graphs_clicked() {
    try {
        double totalIncome = getTotalAmount("income");
        double totalExpense = getTotalAmount("expense");
        double totalTransfers = getTotalAmount("transfer");

        double total = totalIncome + totalExpense + totalTransfers;
        double incomePercentage = (totalIncome / total) * 100;
        double expensePercentage = (totalExpense / total) * 100;
        double transfersPercentage = (totalTransfers / total) * 100;

        PieChart pieChart = new PieChart();
        pieChart.setMaxWidth(1000);
        pieChart.setMaxHeight(500);
        pieChart.setPadding(new Insets(50,200,0,0));
        PieChart.Data incomeData = new PieChart.Data("Income: " + String.format("%.2f", incomePercentage) + "%", incomePercentage);
        PieChart.Data expenseData = new PieChart.Data("Expense: " + String.format("%.2f", expensePercentage) + "%", expensePercentage);
        PieChart.Data transfersData = new PieChart.Data("Transfer: " + String.format("%.2f", transfersPercentage) + "%", transfersPercentage);
        pieChart.getData().addAll(incomeData, expenseData, transfersData);
        if (graph_stack.getChildren().size() > 1) {
            graph_stack.getChildren().remove(1, graph_stack.getChildren().size());
        }
        graph_stack.getChildren().add(pieChart);
        incomeData.getNode().setStyle("-fx-pie-color: #2ca02c;");//green
        expenseData.getNode().setStyle("-fx-pie-color: #ff0000;");//red
        transfersData.getNode().setStyle("-fx-pie-color: #0000FF;");//blue

