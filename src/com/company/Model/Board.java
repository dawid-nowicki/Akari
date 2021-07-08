package com.company.Model;

public class Board {
    private Field[][] fields;

    public Board(int x, int y) {
        this.fields = new Field[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.fields[i][j] = new Field();
            }
        }
    }

    public Field[][] GetFields() {
        return fields;
    }

}
