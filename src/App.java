package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private Scene scene;

    private ButtonArea buttonArea;
    private OutputArea outputContainer;
    private FileMenu toolBar;

    public static void main(String[] args) {
        launch(args);
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

        stage.setOnCloseRequest(event -> {
            if(outputContainer.getOutputAreaChanged()) {
                event.consume();
                ExitWindow exitWindow = new ExitWindow();
                if(exitWindow.spawnExitWindow()) {
                    stage.close();
                }
            }
        });

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