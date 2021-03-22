package game.model;

import game.heuristic.Heuristic;
import tree.Node;

import java.math.BigDecimal;
import java.util.*;

public class State {
    private Integer round = 1;
    private BigDecimal minimalValue;
    private Node currentNode;
    private List<Node> nodesToVisit;
    private List<Node> nodesVisited;
    private Map<Integer, Level> map;
    private Board finish;
    private Node response;

    public Node getNextNode() {
        this.currentNode = this.findNextUnvisited(this.currentNode);
        this.minimalValue = currentNode.getFn();
        if (this.currentNode.getMovements() > 0) {
            this.map.get(this.currentNode.getMovements()).visit();
        }
        this.nodesToVisit.remove(this.currentNode);
        this.nodesVisited.add(this.currentNode);
        return this.currentNode;
    }

    private Node findNextUnvisited(Node node) {
        if (Objects.nonNull(node)) {
            Node fittestChild = node.getChildren().stream()
                    .filter(child -> !this.alreadyVisited(child))
                    .min(new Comparator<Node>() {
                        @Override
                        public int compare(Node n1, Node n2) {
                            return n1.getFn().compareTo(n2.getFn());
                        }
                    })
                    .orElse(null);
//            if (Objects.nonNull(fittestChild) && fittestChild.getFn() <= node.getFn()) {
            if (Objects.nonNull(fittestChild) && fittestChild.getFn().compareTo(node.getFn()) < 1) {
                return fittestChild;
            }
        }
        return this.nodesToVisit.get(0);
    }

    public Node getResponse() {
        return response;
    }

    public void setResponse(Node response) {
        this.response = response;
    }

    private Node findFartherAscendant(Node node) {
        if (node.hasUnvisitedSiblings()) {
            return findFartherAscendant(node.getParent());
        }
        return node.getParent();
    }

    private void addNodeToVisit(Node node) {
        if (!this.alreadyVisited(node) && !this.alreadyInserted(node)) {
            node.getParent().setUnvisitedChilds(Optional.ofNullable(node.getParent().getUnvisitedChilds()).orElse(0) + 1);
            this.nodesToVisit.add(node);
            Level level = Optional.ofNullable(this.map.get(node.getMovements())).orElse(new Level(node.getMovements(), new ArrayList<>()));
            level.add(node);
            this.map.put(node.getMovements(), level);
        }
    }

    private void addNodeVisited(Node node) {
        this.nodesVisited.add(node);
    }

    public void addAllNodeToVisit(List<Node> nodes) {
        for (Node node : nodes) {
            this.addNodeToVisit(node);
        }
        this.round++;
        if ((this.round - 1) % 1000 == 0) {
            this.printState();
        }
        if (this.round - 1 == 100000) {
            this.printState();
            throw new RuntimeException("Too many loops");
        }
    }

    public void addAllNodeVisited(List<Node> nodes) {
        for (Node node : nodes) {
            this.addNodeVisited(node);
        }
    }

    private Boolean alreadyVisited(Node node) {
        return this.nodesVisited.stream()
                .anyMatch(nd -> nd.equals(node));
    }

    private Boolean alreadyInserted(Node node) {
        return this.nodesToVisit.stream()
                .anyMatch(nd -> nd.equals(node));
    }

    public Integer getNodesToVisit() {
        return this.nodesToVisit.size();
    }

    public Integer getNodesVisited() {
        return this.nodesVisited.size();
    }

    public void printInitialState() {
        System.out.println(String.format("Rodada %d", round));
        System.out.println("Nós a serem visitados: " + this.nodesToVisit.size());
        System.out.println("Nós visitados: " + this.nodesVisited.size());
        this.printMatrix(this.nodesToVisit.get(0));
        System.out.println("____________________________________\n");
    }

    public void printState() {
        System.out.println(String.format("Rodada %d", round));
        System.out.println("Nós a serem visitados: " + this.nodesToVisit.size());
        System.out.println("Nós visitados: " + this.nodesVisited.size());
        this.printMatrix(this.currentNode);
//        if (Objects.nonNull(this.response)) {
//            System.out.println("\nResposta");
//            System.out.println(this.response.getCurrentBoard().getValues());
//        }
        System.out.println("____________________________________\n");
    }

    public void printMatrix(Node node) {
        int side = 4;
        for (int i = 0; i < side; i++) {
            System.out.println(String.format(" %2d %2d %2d %2d ", node
                    .getCurrentBoard()
                    .getValues()
                    .subList((i * side), (i * side) + side).toArray()));
        }
        System.out.println(String.format("Movimentações: %d", node.getMovements()));
    }

    public static Builder Builder() { return new Builder(); }

    public static class Builder {
        private Board start;
        private Board finish;
        private Heuristic heuristic;

        public Builder start(Board start) {
            this.start = start;
            return this;
        }

        public Builder finish(Board finish) {
            this.finish = finish;
            return this;
        }

        public Builder heuristic(Heuristic heuristic) {
            this.heuristic = heuristic;
            return this;
        }

        private Node firstNode() {
           return Node.Builder()
                    .board(start)
                    .finish(finish)
                    .movements(0)
                    .heuristic(heuristic)
                    .build();
        }

        public State build() {
            State state = new State();
            state.nodesToVisit = new ArrayList<>(Arrays.asList(firstNode()));
            state.nodesVisited = new ArrayList<>();
            state.map = new HashMap<>();
            return state;
        }
    }
}
