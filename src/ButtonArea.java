package src;

import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ButtonArea extends HBox{

    OutputArea outputArea;
    App app;

    public ButtonArea(App app, OutputArea outputArea) {
        this.app = app;
        this.outputArea = outputArea;
        buildButtonArea();
    }

    private void buildButtonArea() {
        Button findPath = new Button();
        findPath.setText("Find Path");
        findPath.setId("btnFindPath");
        findPathEvent(findPath);

        Button showConnection = new Button();
        showConnection.setText("Show Connection");
        showConnection.setId("btnShowConnection");
        showConnectionEvent(showConnection);

        Button newPlace = new Button();
        newPlace.setText("New Place");
        newPlace.setId("btnNewPlace");
        newPlaceEvent(newPlace);

        Button newConnection = new Button();
        newConnection.setText("New Connection");
        newConnection.setId("btnNewConnection");
        newConnectionEvent(newConnection);

        Button changeConnection = new Button();
        changeConnection.setText("Change Connection");
        changeConnection.setId("btnChangeConnection");
        changeConnectionEvent(changeConnection);

        getChildren().addAll(findPath, showConnection, newPlace, newConnection, changeConnection);
        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        setAlignment(Pos.CENTER);
        setId("buttonArea");
        
    }
    
    private void changeConnectionEvent(Button button) {
        button.setOnAction(event -> {
            outputArea.changeConnection();
        });

    }

    private void newPlaceEvent(Button button) {
        button.setOnAction(event -> {
            outputArea.setCanCreatePlace(true);
            app.setButtonsDisable(true);
            app.setCursorCroshair(true);
        });

    }

    private void newConnectionEvent(Button button) {
        button.setOnAction(event -> {
            outputArea.connectNodes();
        });

    }

    private void findPathEvent(Button button) {
        button.setOnAction(event -> {
            outputArea.findPath();
        });

    }

    private void showConnectionEvent(Button button) {
        button.setOnAction(event -> {
            outputArea.showConnection();
        });

    }
}
