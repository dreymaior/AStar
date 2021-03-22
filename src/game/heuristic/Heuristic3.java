package game.heuristic;

import game.model.Board;

import java.math.BigDecimal;

public class Heuristic3 implements Heuristic{
    @Override
    public BigDecimal getValue(Board node, Board finish) {
        Integer h = 0, index = 0;
        for (Integer value : node.getValues()) {
            Integer reference = finish.getValues().indexOf(value);
            if (!index.equals(reference)) {
                Integer valueLine = index / 4;
                Integer valueColumn = index % 4;
                Integer referenceLine = reference / 4;
                Integer referenceColumn = reference % 4;
                h += this.manhattam(valueLine, valueColumn, referenceLine, referenceColumn);
            }
            index++;
        }
        return BigDecimal.valueOf(h);
    }

    @Override
    public Boolean hasFinished(Board node, Board finish) {
        return this.getValue(node, finish).equals(BigDecimal.ZERO);
    }

    private Integer manhattam(Integer l1, Integer c1, Integer l2, Integer c2) {
        Integer m = 0;
        if (l1 < l2) {
            m += l2 - l1;
        } else {
            m += l1 - l2;
        }
        if (c1 < c2) {
            m += c2 - c1;
        } else {
            m += c1 - c2;
        }
        return m;
    }
}
