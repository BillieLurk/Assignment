package src;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;

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
        openEvents(open);

        MenuItem save = new MenuItem("Save");
        save.setId("menuSaveFile");
        saveEvents(save);

        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setId("menuSaveImage");
        saveMapEvents(saveImage);

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
        newMap.setOnAction(event -> {
            outputArea.newMap("src/europa.gif");
        });
    }

    private void openEvents(MenuItem newMap) {
        newMap.setOnAction(event -> {
            outputArea.loadSave("src/europa.graph");
        });
    }

    private void saveEvents(MenuItem newMap) {
        newMap.setOnAction(event -> {
            outputArea.save("src/europa.graph");
        });
    }

    private void saveMapEvents(MenuItem saveImage) {
        saveImage.setOnAction(event -> {
            outputArea.makeSnapshot();
        });
    }
}
