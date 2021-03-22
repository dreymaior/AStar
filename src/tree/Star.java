package tree;

import game.heuristic.*;
import game.model.Board;
import game.model.State;

import java.util.List;
import java.util.Optional;

public class Star {
    private Board start;
    private Board finish;
    private Heuristic heuristic;
    private State state;

    public State getState() {
        return state;
    }

    public void init() {
        this.state = State.Builder()
                .start(this.start)
                .finish(this.finish)
                .heuristic(this.heuristic)
                .build();

        while (this.state.getNodesToVisit() > 0) {
            Node currentNode = this.state.getNextNode();
            if (currentNode.isFinalState()) {
                this.state.setResponse(currentNode);
                return;
            }

            this.state.addAllNodeToVisit(currentNode.getNextNodesList());
        }
    }

    public static Builder Builder() { return new Builder(); }

    public static class Builder {
        private List<Integer> start;
        private List<Integer> finish;
        private Integer heuristic;

        public Builder start(List<Integer> start) {
            this.start = start;
            return this;
        }

        public Builder finish(List<Integer> finish) {
            this.finish = finish;
            return this;
        }

        public Builder heuristic(Integer heuristic) {
            this.heuristic = heuristic;
            return this;
        }

        private Heuristic getHeuristic() {
            switch (this.heuristic) {
                case 2:
                    return new Heuristic2();
                case 3:
                    return new Heuristic3();
                default:
                    return new Heuristic1();
            }
        }

        public Star build() {
            Star star = new Star();
            Board start = Board.Builder()
                    .values(Optional.ofNullable(this.start)
                                    .orElseThrow(() -> new IllegalArgumentException("Missing start configuration.")))
                    .build();
            Board finish = Board.Builder()
                    .values(Optional.ofNullable(this.finish)
                                    .orElseThrow(() -> new IllegalArgumentException("Missing finish configuration.")))
                    .build();
            star.start = start;
            star.finish = finish;
            star.heuristic = this.getHeuristic();
            return star;
        }
    }
}
