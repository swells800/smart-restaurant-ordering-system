package application;

import javafx.scene.control.Alert;
import java.util.List;
//handles the processing and display of a customer's selected 
public class OrderController {

    public void processOrder(List<MenuItem> selectedItems) {
        if (selectedItems.isEmpty()) {
            showAlert("No items selected!", "Please select at least one item to order.");
            return;
        }

        String summary = "You ordered:\n";
        double total = 0;

        for (MenuItem item : selectedItems) {
            summary += String.format("- %s ($%.2f)\n", item.getName(), item.getPrice());
            total += item.getPrice();
        }	

        summary += String.format("\nTotal: $%.2f", total);

        showAlert("Order Summary", summary);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
