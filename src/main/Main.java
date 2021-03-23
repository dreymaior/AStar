package main;

import tree.Star;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        List<Integer> start = Arrays.asList(0, 1, 5, 9, 2, 6, 10, 13, 3, 7, 11, 14, 4, 8, 12, 15);
//        List<Integer> start = Arrays.asList(2, 1, 5, 9, 3, 6, 10, 13, 4, 7, 11, 14, 0, 8, 12, 15);
//        List<Integer> start = Arrays.asList(2, 1, 10, 9, 3, 5, 11, 13, 4, 6, 12, 14, 0, 7, 8, 15);
        List<Integer> start = Arrays.asList(0, 2, 1, 9, 3, 5, 6, 13, 4, 7, 10, 14, 8, 12, 15, 11);
        List<Integer> finish = Arrays.asList(1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 0);
        Integer heuristic = 3;

        Star star = Star.Builder()
                .heuristic(heuristic)
                .start(start)
                .finish(finish)
                .build();

        star.init();

        star.getState().printState();
    }
}
