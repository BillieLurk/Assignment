package src;

import java.util.ArrayList;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Collation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class ConnectionWindow extends InputDialog {
    private TextField connectionName = new TextField();
    private TextField time = new TextField();

    public ConnectionWindow() {
        super(AlertType.CONFIRMATION);
        
        grid.addRow(0, new Label("Name:"), connectionName);
        grid.addRow(1, new Label("Time:"), time);
    }

    public String getConnectionName() {

        return connectionName.getText();
    }

    public String getTime() {

        return (time.getText());
    }

    public ArrayList<Object> spawnConnectionWindow(ListGraph<Place> listGraph, Place placeA, Place placeB) {
        ArrayList<Object> returnValue = new ArrayList<>();
        try {

            setTitle("Connection");
            setHeaderText("Connection from " + placeA.getName() + " to " + placeB.getName());
            
            //if there is already a connection
            if(listGraph.getEdgeBetween(placeA, placeB) != null) {
                Alert alert = new Alert(AlertType.ERROR, "Error: There is already a connection");
                alert.showAndWait();
                return new ArrayList<>();
            }
            
            Optional<ButtonType> result = showAndWait();
            if (result.get() == ButtonType.OK) {
                //remove should be required in the form
                if(getConnectionName().isEmpty() || getTime().isEmpty()) {
                    Alert alert = new Alert(AlertType.ERROR, "Error: One or more text fields are empty");
                    alert.showAndWait();
                    return new ArrayList<>();
                }
                returnValue.add(getConnectionName());
                returnValue.add(Integer.parseInt(getTime()));
                return returnValue;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Error: Time is not an integer");
            alert.showAndWait();
        }
        return new ArrayList<>();

    }

}