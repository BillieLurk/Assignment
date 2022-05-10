package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FileMenu extends ToolBar {

    App app;
    OutputArea outputArea;
    
    public FileMenu(App app, OutputArea outputArea) {
        this.app = app;
        this.outputArea = outputArea;

        buildToolBar();
    }
    
    private void buildToolBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu");

        MenuItem newMap = new MenuItem("New Map");
        newMap.setId("menuNewMap");
        newMapEvents(newMap);

        MenuItem open = new MenuItem("Open");
        open.setId("menuOpenFile");

        MenuItem save = new MenuItem("Save");
        save.setId("menuSaveFile");

        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setId("menuSaveImage");
        
        MenuItem exit = new MenuItem("Exit");
        exit.setId("menuExit");

        Menu file = new Menu("File");
        file.getItems().addAll(newMap, open, save, saveImage, exit);
        file.setId("menuFile");

        menuBar.getMenus().add(file);
        menuBar.setId("menuBar");

        
        getItems().add(menuBar);
        setPadding(new Insets(0, 0, 0, 0));
    }

    private void newMapEvents(MenuItem newMap) {
        newMap.setOnAction( event -> {
            try {
                outputArea.setImage(new Image(new FileInputStream("src/europa.gif")));

                app.getStage().sizeToScene();
                
                //enable buttons
                app.setButtonsDisable(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
