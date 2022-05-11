package src;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Place extends Circle {
    private String name;
    private boolean selected = false;
    static final int r = 10;
    private static int nrOfSelectedNodes = 0;

    public Place(String name, double x, double y) {
        super(x, y, r);
        this.name = name;
        setFill(Color.BLUE);

        setOnMouseClicked(new ClickHandler());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getNrOfSelectedNodes() {
        return nrOfSelectedNodes;
    }


    @Override
    public String toString() {
        return getName();
    }

    public String toSave() {
        return getName()+";"+getCenterX()+";"+getCenterY()+";";
    }


    class ClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (!selected && nrOfSelectedNodes < 2) {
                setFill(Color.RED);
                selected = true;
                
                nrOfSelectedNodes++;
            } else if (selected) {
                setFill(Color.BLUE);
                selected = false;

                nrOfSelectedNodes--;
            }
        }
    }
}
