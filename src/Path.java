package src;

import javafx.scene.shape.Line;

public class Path extends Line {

    private Place origin;
    private Place destination;
    private String name;
    private int time;

    public Path(Place origin, Place destination, String name, int time) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.time = time;

        setStartX(origin.getCenterX());
        setStartY(origin.getCenterY());
        setEndX(destination.getCenterX());
        setEndY(destination.getCenterY());
    }

    @Override
    public String toString() {
        return getOrigin() + ";" + getDestination() + ";" + getName() + ";" + getTime() + ";";

    }

    public Place getOrigin() {
        return this.origin;
    }

    public Place getDestination() {
        return this.destination;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
