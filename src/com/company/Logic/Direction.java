package com.company.Logic;

import java.util.Random;

public enum Direction {
    LEFT, UP, RIGHT, DOWN;
    private static Direction[] values = values();
    private final static Random RANDOM = new Random();

    public static Direction GetRandomDirection() {
        return values[RANDOM.nextInt(values.length)];
    }
}
