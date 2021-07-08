package com.company.Logic;

import com.company.Model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private static Random random;

    public List<Packet> MakeMap(Board board, DifficultyLevel level) {
        random = new Random();
        int generatedWalls = 0;
        boolean done = false;
        List<Packet> packets = null;
        int board_x = board.GetFields()[0].length;
        int board_y = board.GetFields().length;
        int wallsCount = convertLevel(board_x * board_y, level);

        while (!done) {
            packets = new LinkedList<>();
            generatedWalls = 0;
            int debug = 0;
            while (generatedWalls < wallsCount && debug < board_x * board_x * board_y * board_y) {
                int x_gen = random.nextInt(board.GetFields()[0].length);
                int y_gen = random.nextInt(board.GetFields().length);
                if (board.GetFields()[x_gen][y_gen].GetType() == FiledType.EMPTY && board.GetFields()[x_gen][y_gen].GetValue() == 0) {
                    generateWall(board, x_gen, y_gen);
                    packets.add(new Packet(x_gen, y_gen, 0));
                    generatedWalls++;
                }
                debug++;
            }
            if (generatedWalls == wallsCount) {
                done = true;
            } else {
                board = new Board(board_x, board_y);
            }
        }

        for (int x = 0; x < board.GetFields()[0].length; x++) {
            for (int y = 0; y < board.GetFields().length; y++) {
                if (board.GetFields()[x][y].GetType() == FiledType.EMPTY && board.GetFields()[x][y].GetValue() == 0) {
                    PutRemoveBulb(board, x, y);
                }
            }
        }

        for (Packet packet : packets) {
            int bulbsCount = countBulbsAround(board, packet.GetX(), packet.GetY());
            if (level == DifficultyLevel.HARD && bulbsCount == 0) {
                board.GetFields()[packet.GetX()][packet.GetY()].SetValue(-1);
                packet.SetData(-1);
            } else {
                board.GetFields()[packet.GetX()][packet.GetY()].SetValue(bulbsCount);
                packet.SetData(bulbsCount);
            }
        }

        clearMap(board);
        return packets;
    }

    private void generateWall(Board board, int x, int y) {
        board.GetFields()[x][y].SetType(FiledType.WALL);
        for (int i = 0; i < random.nextInt(4); i++) {
            switch (Direction.GetRandomDirection()) {
                case UP:
                    if (y > 0 && board.GetFields()[x][y - 1].GetType() == FiledType.EMPTY && board.GetFields()[x][y - 1].GetValue() == 0) {
                        PutRemoveBulb(board, x, y - 1);
                    }
                    break;
                case DOWN:
                    if (y < board.GetFields().length - 1 && board.GetFields()[x][y + 1].GetType() == FiledType.EMPTY && board.GetFields()[x][y + 1].GetValue() == 0) {
                        PutRemoveBulb(board, x, y + 1);
                    }
                    break;
                case RIGHT:
                    if (x < board.GetFields()[0].length - 1 && board.GetFields()[x + 1][y].GetType() == FiledType.EMPTY && board.GetFields()[x + 1][y].GetValue() == 0) {
                        PutRemoveBulb(board, x + 1, y);
                    }
                    break;
                case LEFT:
                    if (x > 0 && board.GetFields()[x - 1][y].GetType() == FiledType.EMPTY && board.GetFields()[x - 1][y].GetValue() == 0) {
                        PutRemoveBulb(board, x - 1, y);
                    }
                    break;
            }
        }

    }

    private int countBulbsAround(Board board, int x, int y) {
        int count = 0;
        if (y > 0 && board.GetFields()[x][y - 1].GetType() == FiledType.BULB) count++;
        if (y < board.GetFields().length - 1 && board.GetFields()[x][y + 1].GetType() == FiledType.BULB) count++;
        if (x < board.GetFields()[0].length - 1 && board.GetFields()[x + 1][y].GetType() == FiledType.BULB) count++;
        if (x > 0 && board.GetFields()[x - 1][y].GetType() == FiledType.BULB) count++;
        return count;
    }

    public List<Packet> PutRemoveBulb(Board board, int x, int y) {
        boolean bulbStatus = board.GetFields()[x][y].GetType() == FiledType.BULB;
        board.GetFields()[x][y].SetType(bulbStatus ? FiledType.EMPTY : FiledType.BULB);
        int x_left = x - 1, x_right = x + 1, y_up = y - 1, y_down = y + 1;
        List<Packet> packets = new LinkedList<>();
        int flag = 0;
        while (flag != 0b1111) {
            if (x_left >= 0 && board.GetFields()[x_left][y].GetType() != FiledType.WALL) {
                if (board.GetFields()[x_left][y].ChangeValue(bulbStatus)) {
                    packets.add(new Packet(x_left, y, 0));
                }
                x_left--;
            } else {
                flag = flag | 0b0001;
            }
            if (y_up >= 0 && board.GetFields()[x][y_up].GetType() != FiledType.WALL) {
                if (board.GetFields()[x][y_up].ChangeValue(bulbStatus)) {
                    packets.add(new Packet(x, y_up, 0));
                }
                y_up--;
            } else {
                flag = flag | 0b0010;
            }
            if (x_right < board.GetFields()[0].length && board.GetFields()[x_right][y].GetType() != FiledType.WALL) {
                if (board.GetFields()[x_right][y].ChangeValue(bulbStatus)) {
                    packets.add(new Packet(x_right, y, 0));
                }
                x_right++;
            } else {
                flag = flag | 0b0100;
            }
            if (y_down < board.GetFields().length && board.GetFields()[x][y_down].GetType() != FiledType.WALL) {
                if (board.GetFields()[x][y_down].ChangeValue(bulbStatus)) {
                    packets.add(new Packet(x, y_down, 0));
                }
                y_down++;
            } else {
                flag = flag | 0b1000;
            }
        }
        return packets;
    }

    private void clearMap(Board board) {
        for (Field[] fields : board.GetFields()) {
            for (Field field : fields) {
                if (field.GetType() != FiledType.WALL) {
                    field.SetType(FiledType.EMPTY);
                    field.SetValue(0);
                }
            }
        }
    }

    public List<Packet> CheckMap(Board board) {
        LinkedList<Packet> packets = new LinkedList<>();
        for (int x = 0; x < board.GetFields()[0].length; x++) {
            for (int y = 0; y < board.GetFields().length; y++) {
                switch (board.GetFields()[x][y].GetType()) {
                    case BULB:
                        if (checkBulb(board, x, y))
                            packets.add(new Packet(x, y, 2));
                        break;
                    case WALL:
                        if (checkWall(board, x, y))
                            packets.add(new Packet(x, y, 1));
                        break;
                    case EMPTY:
                        if (board.GetFields()[x][y].GetValue() <= 0)
                            packets.add(new Packet(x, y, 0));
                }
            }
        }
        return packets;
    }

    private boolean checkWall(Board board, int x, int y) {
        if (board.GetFields()[x][y].GetValue() != -1) {
            return board.GetFields()[x][y].GetValue() != countBulbsAround(board, x, y);
        }
        return false;
    }

    private boolean checkBulb(Board board, int x, int y) {
        int flag = 0;
        int x_left = x - 1, x_right = x + 1, y_up = y - 1, y_down = y + 1;

        while (flag != 0b1111) {
            if (x_left >= 0 && board.GetFields()[x_left][y].GetType() != FiledType.WALL) {
                if (board.GetFields()[x_left][y].GetType() == FiledType.BULB) {
                    return true;
                }
                x_left--;
            } else {
                flag = flag | 0b0001;
            }
            if (y_up >= 0 && board.GetFields()[x][y_up].GetType() != FiledType.WALL) {
                if (board.GetFields()[x][y_up].GetType() == FiledType.BULB) {
                    return true;
                }
                y_up--;
            } else {
                flag = flag | 0b0010;
            }
            if (x_right < board.GetFields()[0].length && board.GetFields()[x_right][y].GetType() != FiledType.WALL) {
                if (board.GetFields()[x_right][y].GetType() == FiledType.BULB) {
                    return true;
                }
                x_right++;
            } else {
                flag = flag | 0b0100;
            }
            if (y_down < board.GetFields().length && board.GetFields()[x][y_down].GetType() != FiledType.WALL) {
                if (board.GetFields()[x][y_down].GetType() == FiledType.BULB) {
                    return true;
                }
                y_down++;
            } else {
                flag = flag | 0b1000;
            }
        }
        return false;
    }

    private int convertLevel(int fieldCount, DifficultyLevel level) {
        switch (level) {
            case EASY:
                return fieldCount / 4;
            case NORMAL:
                return fieldCount / 10;
            case HARD:
                return fieldCount / 6;
        }
        return -1;
    }
}
