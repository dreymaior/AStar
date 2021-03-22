package game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Positions {
    public static List<Integer> next(Integer position) {
        List<Integer> positions = new ArrayList<>();
        Integer line    = position / 4;
        Integer column  = position % 4;

        List<Integer> columns = Positions.getColumns(position, column);
        List<Integer> lines = Positions.getLines(position, line);

        positions.add(columns.get(1));  // Direita
        positions.add(lines.get(1));    // Abaixo
        positions.add(columns.get(0));  // Esquerda
        positions.add(lines.get(0));    // Acima
        return positions.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<Integer> getLines(Integer position, Integer line) {
        if (line == 0) {
            // Apenas linha abaixo
            return Arrays.asList(null, position + 4);
        } else if (line == 3) {
            // Apenas linha acima
            return Arrays.asList(position - 4, null);
        }
        return Arrays.asList(position - 4, position + 4);
    }

    private static List<Integer> getColumns(Integer position, Integer column) {
        if (column == 0) {
            // Apenas coluna a direita
            return Arrays.asList(null, position + 1);
        } else if (column == 3) {
            // Apenas coluna a esquerda
            return Arrays.asList(position - 1, null);
        }
        return Arrays.asList(position - 1, position + 1);
    }
}
