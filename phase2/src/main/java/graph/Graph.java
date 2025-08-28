package graph;

import lombok.Getter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import org.javatuples.Pair;

public class Graph {
    @Getter
    private ArrayList<Node> graph;

    public Graph(ArrayList<Node> graph) {
        this.graph = graph;
    }

    public void resetVisits() {
        // Inlined simple loop instead of separate method calls
        graph.forEach(node -> node.setVisited(false));
    }

    public void bfs(Node startNode) {
        // Inline resetVisits call since it's simple
        graph.forEach(node -> node.setVisited(false));

        Queue<Pair<Node, Integer>> nodeQueue = new LinkedList<>();
        nodeQueue.add(new Pair<>(startNode, 0));

        while (!nodeQueue.isEmpty()) {
            Pair<Node, Integer> currentPair = nodeQueue.poll();
            Node currentNode = currentPair.getValue0();

            if (!currentNode.isVisited()) {
                currentNode.setVisited(true);
                int currentDistance = currentPair.getValue1();
                currentNode.setDistance(currentDistance);

                // Inline the neighbor processing instead of separate method
                nodeQueue.addAll(
                        currentNode.getAvailableNeighbors()
                                .stream()
                                .map(neighbor -> new Pair<>(neighbor, currentDistance + 1))
                                .collect(Collectors.toList())
                );
            }
        }
    }

    public void dijkstra(Node startNode) {
        // Inline resetVisits call
        graph.forEach(node -> node.setVisited(false));

        PriorityQueue<Pair<Integer, Node>> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Pair<>(0, startNode));

        while (!priorityQueue.isEmpty()) {
            Pair<Integer, Node> currentPair = priorityQueue.poll();
            Node currentNode = currentPair.getValue1();

            if (!currentNode.isVisited()) {
                currentNode.setVisited(true);
                int currentDistance = currentPair.getValue0();
                currentNode.setDistance(currentDistance);

                // Inline the weighted neighbor processing
                priorityQueue.addAll(
                        currentNode.getAvailableWeightedNeighbors()
                                .stream()
                                .map(neighbor -> new Pair<>(
                                        neighbor.getValue1() + currentDistance,
                                        neighbor.getValue0()
                                ))
                                .collect(Collectors.toList())
                );
            }
        }
    }
}
