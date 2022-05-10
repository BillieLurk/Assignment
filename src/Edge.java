package src;
import java.util.Objects;
import java.io.Serializable;

public class Edge<T> implements Serializable {

    private final T destination;
    private final String connection;
    private int weight;

    public Edge(T destination, String connection, int weight) {
        this.destination = Objects.requireNonNull(destination);
        this.connection = Objects.requireNonNull(connection);
        setWeight(weight);
    }

    public T getDestination() {
        return this.destination;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        
        if(weight < 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return this.connection;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge<?> edge) {
            return Objects.equals(connection, edge.connection) &&
                    Objects.equals(destination, edge.destination);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, connection);
    }

    @Override
    public String toString() {
        return 
            " till " + destination + 
            " med " + getName() +
            " tar " + getWeight();
    }


}
