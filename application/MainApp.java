package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
import java.io.File;

public class MainApp extends Application {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private final List<MenuItem> selectedItems = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        initializeMenuItems();
        showWelcomePage(stage);
    }
    // add text file
    private void initializeMenuItems() {
        try {
            Scanner scanner = new Scanner(new File("menu.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    menuItems.add(new MenuItem(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim()));
                 
                }else {
                	showErrorAlert("Invalid Line Format", "Invalid line format: " + Arrays.toString(parts));

                }
               
            }
            scanner.close();
        } catch (Exception e) {
            showErrorAlert("Error", "Failed to load the menu file. Please check if 'menu.txt' exists.");
        }
    }


        private void showErrorAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
        	
    private void showWelcomePage(Stage stage) {
        Label welcomeLabel = new Label("Welcome to Ty's Restaurant!");
        welcomeLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label instructionLabel = new Label("Click a button to get started.");
        instructionLabel.setStyle("-fx-font-size: 16px;");

        Button startOrderButton = new Button("Start Order");
        startOrderButton.setOnAction(_ -> showMenuPage(stage));

        Button previewMenuButton = new Button("Preview Menu");
        previewMenuButton.setOnAction(_ -> showMenuDisplayPage(stage));

        VBox layout = new VBox(20, welcomeLabel, instructionLabel, startOrderButton, previewMenuButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Welcome");
        stage.show();
    }

    private void showMenuPage(Stage stage) {

            selectedItems.clear();

            Label title = new Label("Smart Restaurant Menu");
            title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Map<String, VBox> categoryMap = new HashMap<>();

            for (int i = 0; i < menuItems.size(); i++) {
                MenuItem item = menuItems.get(i);
                String category = item.getCategory();
                categoryMap.putIfAbsent(category, new VBox(5));

                String label = item.getName() + " - $" + String.format("%.2f", item.getPrice());

                CheckBox checkBox = new CheckBox(label);

                checkBox.setOnAction(_ -> {
                    if (checkBox.isSelected()) {
                        selectedItems.add(item);
                    } else {
                        selectedItems.remove(item);
                    }
                });

                categoryMap.get(category).getChildren().add(checkBox);
            }

            String[] categories = { "Main Dish", "Sides", "Drinks", "Condiments" };
            VBox menuBox = new VBox(15);

            for (int i = 0; i < categories.length; i++) {
                String category = categories[i];
                if (categoryMap.containsKey(category)) {
                    Label categoryLabel = new Label(category);
                    categoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                    VBox section = new VBox(5, categoryLabel, categoryMap.get(category));
                    menuBox.getChildren().add(section);
                }
            }

            ScrollPane scrollPane = new ScrollPane(menuBox);
            scrollPane.setFitToWidth(true);

            Button orderButton = new Button("Place Order");
            orderButton.setOnAction(_ -> showOrderConfirmation(stage));

            Button backButton = new Button("Back");
            backButton.setOnAction(_ -> showWelcomePage(stage));

            Button clearButton = new Button("Clear Selections");
            clearButton.setOnAction(_ -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Clear Selections");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to clear your selections?");

                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");

                alert.getButtonTypes().setAll(yesButton, noButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        selectedItems.clear();
                        showMenuPage(stage); // Reload the page
                    }
                });
            });

            VBox layout = new VBox(15, title, scrollPane, orderButton, clearButton, backButton);
            layout.setAlignment(Pos.TOP_CENTER);
            layout.setStyle("-fx-padding: 15;");

            Scene scene = new Scene(layout, 400, 500);
            stage.setScene(scene);
            stage.setTitle("Smart Restaurant Menu");
        }


    private void showOrderConfirmation(Stage stage) {
        Label confirmLabel = new Label("Please confirm your order:");
        confirmLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox itemList = new VBox(5);
        double total = 0.0;

        for (int i = 0; i < selectedItems.size(); i++) {
            MenuItem item = selectedItems.get(i);
            Label itemLabel = new Label(item.getName() + " - $" + item.getPrice());
            itemList.getChildren().add(itemLabel);
            total += item.getPrice();
        }

        Label totalLabel = new Label("Total: $" + String.format("%.2f", total));
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(_ -> {
            OrderController controller = new OrderController();
            controller.processOrder(selectedItems);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(_ -> showMenuPage(stage));

        VBox layout = new VBox(15, confirmLabel, itemList, totalLabel, confirmButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Confirm Your Order");
    }

    private void showMenuDisplayPage(Stage stage) {
        Label menuLabel = new Label("Preview Menu:");
        menuLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox menuList = new VBox(5);

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            Label label = new Label(item.getName() + " - $" + item.getPrice());
            menuList.getChildren().add(label);
        }

        Button backButton = new Button("Back to Welcome Page");
        backButton.setOnAction(_ -> showWelcomePage(stage));

        VBox layout = new VBox(15, menuLabel, menuList, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Menu Preview");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

