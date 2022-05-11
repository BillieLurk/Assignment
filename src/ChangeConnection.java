package src;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

class ChangeConnection extends InputDialog {
    private TextField connectionName = new TextField();
    private TextField time = new TextField();

    public ChangeConnection() {
        super(AlertType.INFORMATION);
        connectionName.setEditable(false);
        grid.addRow(0, new Label("Name:"), connectionName);
        grid.addRow(1, new Label("Time:"), time);
    }

    public String getTime() {

        return (time.getText());
    }

    public int spawnChangeConnectionWindow(ListGraph<Place> listGraph, Place placeA, Place placeB) {
        Edge<Place> edge = listGraph.getEdgeBetween(placeA, placeB);
        int timeInt = 0;
        if (edge != null) {
            connectionName.setText(edge.getName());
            setTitle("Connection");
            setHeaderText("Connection from " + placeA.getName() + " to " + placeB.getName());
            Optional<ButtonType> result = showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    timeInt = Integer.parseInt(time.getText());
                } catch (final NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR, "Error: Please choose a new time");
                    alert.showAndWait();
                    return 0;
                }
                if (getTime().isEmpty() || timeInt == 0) {
                    Alert alert = new Alert(AlertType.ERROR, "Error: Please choose a new time");
                    alert.showAndWait();
                    return 0;
                } else {
                    return timeInt;
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: No connection!");
            alert.showAndWait();
        }
        return 0;
    }
}
