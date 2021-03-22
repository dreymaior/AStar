package game.heuristic;

import game.model.Board;
import tree.Node;

import java.math.BigDecimal;
import java.util.List;

public interface Heuristic {
    BigDecimal getValue(Board node, Board finish);

    Boolean hasFinished(Board node, Board finish);
}
