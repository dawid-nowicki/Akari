package com.company.Model;

public class Packet {
    int x, y, data;

    public Packet(int x, int y, int data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public int GetData() {
        return data;
    }

    public void SetData(int data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        Packet toCompare = (Packet) obj;
        return this.x == toCompare.GetX() && this.y == toCompare.GetY();
    }
}
