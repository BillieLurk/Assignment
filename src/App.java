package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private Scene scene;

    private ButtonArea buttonArea;
    private OutputArea outputContainer;
    private FileMenu toolBar;

    public static void main(String[] args) {
        launch(args);
        //hej erik
    }

    @Override
    public void start(Stage primStage) throws Exception {
        
        stage = primStage;
        primStage.setTitle("PathFinder");
        primStage.setResizable(false);

        outputContainer = new OutputArea(this);

        buttonArea = new ButtonArea(this, outputContainer);

        toolBar = new FileMenu(this, outputContainer);

        VBox root = new VBox(toolBar, buttonArea, outputContainer);

        root.setSpacing(0);
        root.setPadding(new Insets(0, 0, 0, 0));

        scene = new Scene(root);
        primStage.setScene(scene);
        primStage.show();

        setButtonsDisable(true);
    }

    public Stage getStage() {
        return stage;
    }

    public void setButtonsDisable(boolean value) {
        for (Node tmp : buttonArea.getChildren()) {

            Button button = (Button) tmp;
            button.setDisable(value);
        }
    }

    

    public void setCursorCroshair(Boolean value) {
        if (value == true) {
            scene.setCursor(Cursor.CROSSHAIR);
        } else {
            scene.setCursor(Cursor.DEFAULT);
        }
    }

    
}