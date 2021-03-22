package tree;

import game.heuristic.Heuristic;
import game.model.Board;
import game.model.Positions;

import java.math.BigDecimal;
import java.util.*;

public class Node {
    private Node parent;
    private Board currentBoard;
    private Board finish;
    private Integer movements;
    private Heuristic heuristic;
    private List<Node> children;
    private Integer unvisitedChilds;

    public Node getParent() {
        return parent;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Integer getMovements() {
        return movements;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Boolean hasUnvisitedSiblings() {
        if (Objects.nonNull(this.parent)) {
            return this.parent.hasUnvisitedChilds();
        }
        return false;
    }

    public Boolean hasUnvisitedChilds() {
        return Optional.ofNullable(unvisitedChilds).orElse(0) > 0;
    }

    public Integer getUnvisitedChilds() {
        return Optional.ofNullable(unvisitedChilds).orElse(0);
    }

    public void setUnvisitedChilds(Integer unvisitedChilds) {
        this.unvisitedChilds = unvisitedChilds;
    }

    public BigDecimal getValue() {
        return this.getHeuristic().getValue(this.getCurrentBoard(), this.finish);
    }

    public BigDecimal getFn() {
        return this.getValue().add(BigDecimal.valueOf(this.getMovements()));
    }

    public Boolean isFinalState() {
        return this.getHeuristic().hasFinished(this.getCurrentBoard(), this.finish);
    }

    public List<Node> getNextNodesList() {
        Integer zeroPosition = this.getCurrentBoard().getZeroPosition();
        List<Node> nextNodes = new ArrayList<>();
        List<Integer> nextPositions = Positions.next(zeroPosition);
        for (Integer nextPosition : nextPositions) {
            Board nextBoard = Board.Builder()
                    .values(new ArrayList<>(this.getCurrentBoard().getValues()))
                    .build();
            Integer aux = nextBoard.getValues().get(zeroPosition);
            nextBoard.getValues().set(zeroPosition, nextBoard.getValues().get(nextPosition));
            nextBoard.getValues().set(nextPosition, aux);
            Node nextNode = Node.Builder()
                    .parent(this)
                    .board(nextBoard)
                    .finish(this.finish)
                    .movements(this.getMovements() + 1)
                    .heuristic(this.getHeuristic())
                    .build();
            nextNodes.add(nextNode);
        }
        this.children = nextNodes;
        return nextNodes;
    }

    public static Builder Builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return currentBoard.equals(node.currentBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBoard);
    }

    public static class Builder {
        private Node parent;
        private Board currentBoard;
        private Board finish;
        private Integer movements;
        private Heuristic heuristic;

        public Builder parent(Node parent) {
            this.parent = parent;
            return this;
        }

        public Builder board(Board board) {
            this.currentBoard = board;
            return this;
        }

        public Builder finish(Board finish) {
            this.finish = finish;
            return this;
        }

        public Builder movements(Integer movements) {
            this.movements = movements;
            return this;
        }

        public Builder heuristic(Heuristic heuristic) {
            this.heuristic = heuristic;
            return this;
        }

        public Node build() {
            Node node = new Node();
            node.parent = parent;
            node.currentBoard = currentBoard;
            node.finish = finish;
            node.movements = movements;
            node.heuristic = heuristic;
            return node;
        }
    }
}
