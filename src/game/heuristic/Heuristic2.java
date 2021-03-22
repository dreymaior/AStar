package game.heuristic;

import game.model.Board;

import java.math.BigDecimal;

public class Heuristic2 implements Heuristic{
    @Override
    public BigDecimal getValue(Board node, Board finish) {
        Integer h = 0, index = 0;
        for (Integer value : node.getValues()) {
            if ((++index) % 4 != 0 && value != 0) {
                Integer reference = finish.getValues().indexOf(value);
                if (!node.getValues().get(index).equals(finish.getValues().get(reference + 1))) {
                    h++;
                }
            }
        }
        return BigDecimal.valueOf(h);
    }

    @Override
    public Boolean hasFinished(Board node, Board finish) {
        return this.getValue(node, finish).equals(BigDecimal.ZERO);
    }
}
