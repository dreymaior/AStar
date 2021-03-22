package game.heuristic;

import game.model.Board;

import java.math.BigDecimal;

public class Heuristic1 implements Heuristic {
    @Override
    public BigDecimal getValue(Board node, Board finish) {
        Integer h = node.getValues().size();
        for (Integer value : node.getValues()) {
            if (finish.getValues().get(node.getValues().indexOf(value)).equals(value)) {
                h--;
            }
        }
        return BigDecimal.valueOf(h);
    }

    @Override
    public Boolean hasFinished(Board node, Board finish) {
        return this.getValue(node, finish).equals(BigDecimal.ZERO);
    }
}
