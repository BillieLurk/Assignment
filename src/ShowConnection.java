package src;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class ShowConnection extends InputDialog{
    private TextField connectionName = new TextField();
    private TextField time = new TextField();
    public ShowConnection() {
        super(AlertType.INFORMATION);
        connectionName.setEditable(false);
        time.setEditable(false);
        grid.addRow(0, new Label("Name:"), connectionName);
        grid.addRow(1, new Label("Time:"), time);
    }
    public void spawnShowNewConnectionWindow(ListGraph<Place> listGraph, Place placeA, Place placeB){
        
        Edge<Place> edge = listGraph.getEdgeBetween(placeA, placeB);
        if(edge != null) {
        connectionName.setText(edge.getName());
        time.setText(edge.getWeight()+"");
        setTitle("Connection");
        setHeaderText("Connection from "+ placeA.getName() + " to "+ placeB.getName());
        Optional<ButtonType> result = showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            return;
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: No connection!");
            alert.showAndWait();
        }
    }
}
