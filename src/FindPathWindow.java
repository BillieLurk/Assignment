package src;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

class FindPathWindow extends InputDialog {

    private TextArea textArea = new TextArea();

    public FindPathWindow() {
        super(AlertType.INFORMATION);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setEditable(false);
        grid.addRow(0, textArea);
    }

    public void setTextArea(String text) {
        textArea.setText(text);
    }

    public void spawnFindPathWindow(ListGraph<Place> listGraph, Place placeA, Place placeB) {
        setTitle("Connection");
        setHeaderText("The Path from " + placeA.getName() + " to " + placeB.getName() + ":");

        int totalTime = 0;

        List<Edge<Place>> paths = listGraph.getPath(placeA, placeB);
        if (paths != null) {
            StringBuilder sb = new StringBuilder();
            for (Edge<Place> edge : paths) {
                totalTime += edge.getWeight();
                sb.append(edge.toString() + "\n");
            }
            sb.append("Total " + totalTime);

            setTextArea(sb.toString());

            Optional<ButtonType> result = showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Error: No connection!");
            alert.showAndWait();
        }
    }
}