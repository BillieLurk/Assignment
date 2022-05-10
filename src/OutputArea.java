package src;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class OutputArea extends Pane implements Serializable {

    private ArrayList<Place> places = new ArrayList<>();
    private HashMap<Place, Text> lables = new HashMap<>();
    private boolean canCreatePlace = false;

    private ImageView imageView;

    private App app;

    private ListGraph<Place> listGraph;

    public OutputArea(App app) {
        this.app = app;
        this.imageView = buildImageView();
        getChildren().add(this.imageView);

        setOnMouseClicked(new ClickHandler());

        listGraph = new ListGraph<>();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setCanCreatePlace(boolean value) {

        app.setButtonsDisable(value);
        app.setCursorCroshair(value);
        canCreatePlace = value;
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    private ImageView buildImageView() {
        ImageView imageView = new ImageView();
        imageView.setId("outputArea");

        return imageView;
    }

    public void createPlace(MouseEvent event, String name) {

        Place place = new Place(name, event.getX(), event.getY());
        Text lable = new Text(name);
        lable.setX(event.getX() + 10);
        lable.setY(event.getY() + 10);
        lables.put(place, lable);
        places.add(place);
        getChildren().add(place);
        getChildren().add(lable);

        listGraph.add(place);
    }

    public ArrayList<Place> getSelectedPlaces() {
        ArrayList<Place> selected = new ArrayList<>();
        for (Place place : places) {
            if (place.isSelected()) {
                selected.add(place);
            }
        }
        return selected;
    }

    public void connectNodes() {
        if (getSelectedPlaces().size() >= 2) {

            Place placeA = getSelectedPlaces().get(0);
            Place placeB = getSelectedPlaces().get(1);

            ConnectionWindow dialog = new ConnectionWindow();
            ArrayList<Object> values = dialog.spawnConnectionWindow(listGraph ,placeA, placeB);

            if (!values.isEmpty()) {
                listGraph.connect(placeA, placeB, (String) values.get(0), (int) values.get(1));

                Line line = new Line();
                line.setStartX(placeA.getCenterX());
                line.setStartY(placeA.getCenterY());
                line.setEndX(placeB.getCenterX());
                line.setEndY(placeB.getCenterY());
                line.setStrokeWidth(5);
                getChildren().add(line);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: Two places must be selected");
            alert.showAndWait();
        }
    }

    public void findPath() {
        if (getSelectedPlaces().size() >= 2) {

            Place placeA = getSelectedPlaces().get(0);
            Place placeB = getSelectedPlaces().get(1);

            FindPathWindow dialog = new FindPathWindow();
            dialog.spawnFindPathWindow(listGraph, placeA, placeB);
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: Two places must be selected");
            alert.showAndWait();
        }
    }

    public void showConnection() {
        if (getSelectedPlaces().size() >= 2) {
            Place placeA = getSelectedPlaces().get(0);
            Place placeB = getSelectedPlaces().get(1);

            ShowConnection dialog = new ShowConnection();
            dialog.spawnShowNewConnectionWindow(listGraph, placeA, placeB);
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: Two places must be selected");
            alert.showAndWait();
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (canCreatePlace) {

                NewPlaceDialog dialog = new NewPlaceDialog();
                String name = dialog.spawnNewPlaceDialog();
                if (!name.isEmpty())
                    createPlace(event, name);
                setCanCreatePlace(false);
            }
        }
    }
}
