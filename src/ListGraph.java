package src;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ListGraph<T> implements Graph<T>, Serializable {


    private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

   
    @Override
    public void remove(T node) {
        try {
            Set<Edge<T>> edges = (Set<Edge<T>>) getEdgesFrom(node);
            for (Edge<T> edge : edges) {

                Iterator<Edge<T>> it = nodes.get(edge.getDestination()).iterator();
                while (it.hasNext()) {
                    if (it.next().getDestination().equals(node)) {
                        it.remove();
                    }
                }
            }
            nodes.remove(node);
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {

        Set<Edge<T>> edgesA = nodes.get(node1);
        Set<Edge<T>> edgesB = nodes.get(node2);

        try {
            var newA = new Edge<>(node2, name, weight);
            var newB = new Edge<>(node1, name, weight);

            if (edgesA.contains(newA) && edgesB.contains(newB)) {
                throw new IllegalStateException();
            } else {
                edgesA.add(newA);
                edgesB.add(newB);
            }
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        try {
            getEdgeBetween(node1, node2).setWeight(weight);
            getEdgeBetween(node2, node1).setWeight(weight);
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Set<T> getNodes() {
        return Collections.unmodifiableSet(nodes.keySet());
    }
    
   
    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        try {
            return Collections.unmodifiableSet(nodes.get(node));
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!(nodes.containsKey(node1) && nodes.containsKey(node2)))
            throw new NoSuchElementException();
        for (Edge<T> edge : nodes.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {
        if (!nodes.get(node1).remove(getEdgeBetween(node1, node2))) {
            throw new IllegalStateException();
        }
        nodes.get(node2).remove(getEdgeBetween(node2, node1));
    }

    @Override
    public boolean pathExists(T from, T to) {
        try {
            Set<T> visited = new HashSet<>();
            depthFirstVisitAll(from, visited);
            return visited.contains(to);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void depthFirstVisitAll(T object, Set<T> visited) {
        visited.add(object);
        for (Edge<T> edge : nodes.get(object)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll((T) edge.getDestination(), visited);
            }
        }
    }

    private void depthFirstConnection(T to, T from, Map<T, T> connection) {
        connection.put(to, from);
        for (Edge<T> edge : nodes.get(to)) {
            if (!connection.containsKey(edge.getDestination())) {
                depthFirstConnection((T) edge.getDestination(), to, connection);
            }
        }

    }

    private List<Edge<T>> gatherPath(T from, T to, Map<T, T> connection) {
        LinkedList<Edge<T>> path = new LinkedList<>();
        T current = to;
        while (!current.equals(from)) {
            T next = connection.get(current);
            Edge<T> edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return (!path.isEmpty()) ? Collections.unmodifiableList(path) : null;
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {

        Map<T, T> connection = new HashMap<>();
        depthFirstConnection(from, null, connection);
        if (!connection.containsKey(to)) {
            return null;
        }
        
        return gatherPath(from, to, connection);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T node : nodes.keySet()) {
            sb.append(node).append(": ").append(nodes.get(node)).append("\n");
        }
        return sb.toString();
    }

}
