package game.model;

import game.heuristic.Heuristic;
import tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    private List<Integer> values;

    public List<Integer> getValues() {
        return values;
    }

    public Integer getZeroPosition() {
        return values.indexOf(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return values.equals(board.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Integer> values;

        public Builder values(List<Integer> values) {
            this.values = values;
            return this;
        }

        public Board build() {
            Board board = new Board();
            board.values = this.values;
            return board;
        }
    }
}
