package com.company.Model;

public class Field {
    private FiledType type;
    private int value;

    public Field() {
        this.type = FiledType.EMPTY;
    }

    public FiledType GetType() {
        return this.type;
    }

    public int GetValue() {
        return value;
    }

    public void SetType(FiledType type) {
        this.type = type;
    }

    public void SetValue(int value) {
        this.value = value;
    }

    /**
     * True if field changed - unlit to lit or lit to unlit
     */
    public boolean ChangeValue(boolean bulbStatus) {
        return bulbStatus ? --value == 0 : ++value == 1;
    }


}
