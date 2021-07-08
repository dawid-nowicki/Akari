package com.company.Logic;

import com.company.Model.Board;
import com.company.Model.DifficultyLevel;
import com.company.Model.Packet;

import java.util.List;

public class Controller {
    private Board board;
    private MapGenerator mapGenerator;

    public Controller() {
        this.mapGenerator = new MapGenerator();
    }

    public List<Packet> CreateMap(int x, int y, DifficultyLevel level) {
        this.board = new Board(x, y);
        return mapGenerator.MakeMap(board, level);
    }

    public List<Packet> PutRemoveBulb(int x, int y) {

        return mapGenerator.PutRemoveBulb(board, x, y);
    }

    public List<Packet> CheckMap() {
        return mapGenerator.CheckMap(board);
    }
}
