package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.print.Collation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class OutputArea extends Pane implements Serializable {

    private ArrayList<Place> places = new ArrayList<>();
    private HashMap<String, Place> placeByName = new HashMap<>();
    private ArrayList<Path> paths = new ArrayList<>();
    private ArrayList<Text> labels = new ArrayList<>();

    private boolean canCreatePlace = false;

    private ImageView imageView;

    private App app;

    private String imageURL;

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

    public void setImage(String imageURL) {
        this.imageURL = imageURL;
        try {
            imageView.setImage(new Image(new FileInputStream(imageURL)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        app.getStage().sizeToScene();
        // enable buttons
        app.setButtonsDisable(false);
    }

    public void newMap(String imageURL) {
        this.imageURL = imageURL;
        listGraph = new ListGraph<>();
        getChildren().removeAll(places);
        getChildren().removeAll(paths);
        getChildren().removeAll(labels);
        try {
            imageView.setImage(new Image(new FileInputStream(imageURL)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        app.getStage().sizeToScene();
        // enable buttons
        app.setButtonsDisable(false);
    }

    private ImageView buildImageView() {
        ImageView imageView = new ImageView();
        imageView.setId("outputArea");

        return imageView;
    }

    private String getPlacesToSave() {
        StringBuilder sb = new StringBuilder();
        for (Place place : places) {
            sb.append(place.toSave());
        }
        return "\n"+sb.toString();
    }

    private String getFileSave() {
        return "file:" + imageURL;
    }

    private String getPathSave() {
        StringBuilder sb = new StringBuilder();

        for (Path path : paths) {
            sb.append("\n"+path.toString());
        }
        return sb.toString();
    }

    public String getSave() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFileSave());
        sb.append(getPlacesToSave());
        sb.append(getPathSave());
        return sb.toString();
    }

    public void save(String toFile) {
        SaveHandler sh = new SaveHandler(this);
        sh.saveGraph(toFile);
    }

    public void createPlace(MouseEvent event, String name) {

        Place place = new Place(name, event.getX(), event.getY());
        Text lable = new Text(name);
        lable.setX(place.getCenterX() + 10);
        lable.setY(place.getCenterY() + 10);
        
        places.add(place);
        getChildren().add(place);
        getChildren().add(lable);
        labels.add(lable);

        listGraph.add(place);
    }

    public void loadPaths(ArrayList<Path> arr) {
        for (Path path : arr) {
            try {
                listGraph.connect(path.getOrigin(), path.getDestination(), path.getName(), path.getTime());
                paths.add(path);
                path.setStrokeWidth(5);
                getChildren().add(path);
            }            
            catch (Exception e) {
                System.out.println("path already exists skipping the bs");
            }
                
        }
    }

    public void loadPlaces(ArrayList<Place> arr) {

        for (Place place : arr) {

            Text lable = new Text(place.getName());
            lable.setX(place.getCenterX() + 10);
            lable.setY(place.getCenterY() + 10);
            
            places.add(place);
            getChildren().add(place);
            getChildren().add(lable);
            labels.add(lable);

            listGraph.add(place);
        }
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
            ArrayList<Object> values = dialog.spawnConnectionWindow(listGraph, placeA, placeB);

            if (!values.isEmpty()) {
                listGraph.connect(placeA, placeB, (String) values.get(0), (int) values.get(1));

                Path path = new Path(placeA, placeB, (String) values.get(0), (int) values.get(1));
                paths.add(path);
                path.setStrokeWidth(5);
                getChildren().add(path);
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

    public void changeConnection() {
        if (getSelectedPlaces().size() >= 2) {
            Place placeA = getSelectedPlaces().get(0);
            Place placeB = getSelectedPlaces().get(1);

            ChangeConnection dialog = new ChangeConnection();
            int newTime = dialog.spawnChangeConnectionWindow(listGraph , placeA, placeB);
            if (newTime != 0) {
                Edge<Place> edge = listGraph.getEdgeBetween(placeA, placeB);
                listGraph.disconnect(placeA, placeB);
                listGraph.connect(placeA, placeB, edge.getName(), newTime);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: Two places must be selected");
            alert.showAndWait();
        }
    }

    public void loadSave(String path) {
        SaveHandler sh = new SaveHandler(this);
        sh.loadGraph(path);
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
