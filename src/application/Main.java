package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {

    private int currentBalance = 0; 

    @Override
    public void start(Stage primaryStage) {
        try {
            // Root layout
            VBox root = new VBox(10);
            root.setStyle("-fx-padding: 10; -fx-alignment: top-center;");

           
            StringBuilder productList = new StringBuilder("Available Products:\n");
            for (Product product : Product.values()) {
                if (product != Product.EMPTY) {
                    productList.append(product.name())
                               .append(" - ")
                               .append(product.getPrice())
                               .append(" cents\n");
                }
            }

            
            Label productListLabel = new Label(productList.toString());
            Label balanceLabel = new Label("Current Balance: 0 cents");

            TextField moneyInput = new TextField();
            moneyInput.setPromptText("Enter amount to add (in cents)");

            TextField productInput = new TextField();
            productInput.setPromptText("Enter the product name");

            Label outputLabel = new Label();

            
            ProgressBar balanceBar = new ProgressBar(0);
            balanceBar.setPrefWidth(300);

            
            Button addMoneyButton = new Button("Add Money");
            Tooltip addMoneyTooltip = new Tooltip("Click to add money to your balance");
            Tooltip.install(addMoneyButton, addMoneyTooltip);

            addMoneyButton.setOnAction(_ -> {
                String moneyText = moneyInput.getText().trim();
                if (moneyText.isEmpty()) {
                    outputLabel.setText("Please enter an amount to add.");
                    return;
                }

                int moneyToAdd;
                try {
                    moneyToAdd = Integer.parseInt(moneyText);
                } catch (NumberFormatException e) {
                    outputLabel.setText("Invalid amount. Please enter a number.");
                    return;
                }

                if (moneyToAdd <= 0) {
                    outputLabel.setText("Please add a positive amount.");
                } else {
                    currentBalance += moneyToAdd;
                    balanceLabel.setText("Current Balance: " + currentBalance + " cents");
                    balanceBar.setProgress(Math.min(1.0, currentBalance / 100.0)); // Assuming 100 is the max balance
                    outputLabel.setText("Added " + moneyToAdd + " cents to your balance.");
                }
            });

            Button selectItemButton = new Button("Select Item");
            Tooltip selectItemTooltip = new Tooltip("Enter the product name and click to buy");
            Tooltip.install(selectItemButton, selectItemTooltip);

            selectItemButton.setOnAction(_ -> {
                String productName = productInput.getText().trim();
                if (productName.isEmpty()) {
                    outputLabel.setText("No product selected. Please try again.");
                    return;
                }

                Product selectedProduct;
                try {
                    selectedProduct = Product.valueOf(productName.toUpperCase());
                } catch (IllegalArgumentException e) {
                    outputLabel.setText("Invalid product: " + productName);
                    return;
                }

                int price = selectedProduct.getPrice();

                if (currentBalance >= price) {
                    currentBalance -= price;
                    balanceLabel.setText("Current Balance: " + currentBalance + " cents");
                    balanceBar.setProgress(Math.min(1.0, currentBalance / 100.0)); // Update balance bar

                    // Show a popup alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Purchase Complete");
                    alert.setHeaderText("Thank you for your purchase!");
                    alert.setContentText("Product: " + selectedProduct.name() + "\n" +
                                         "Price: " + price + " cents\n" +
                                         "Remaining Balance: " + currentBalance + " cents");
                    alert.showAndWait();

                    outputLabel.setText("Thank you for your purchase!");
                } else {
                    outputLabel.setText("Insufficient balance! This product costs " + price + " cents.");
                }
            });

            Button getChangeButton = new Button("Get Change");
            getChangeButton.setOnAction(_ -> {
                if (currentBalance > 0) {
                    outputLabel.setText("Returning your change: " + currentBalance + " cents.");
                    currentBalance = 0; // Reset balance
                    balanceLabel.setText("Current Balance: " + currentBalance + " cents");
                    balanceBar.setProgress(0); // Reset progress bar
                } else {
                    outputLabel.setText("No balance to return.");
                }
            });

            // Layout the elements
            root.getChildren().addAll(
                    productListLabel,
                    balanceLabel,
                    balanceBar, 
                    moneyInput,
                    addMoneyButton,
                    productInput,
                    selectItemButton,
                    getChangeButton,
                    outputLabel
            );

            // Create and show the scene
            Scene scene = new Scene(root, 400, 450);
            primaryStage.setTitle("Vending Machine");
            primaryStage.setScene(scene);

          
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
