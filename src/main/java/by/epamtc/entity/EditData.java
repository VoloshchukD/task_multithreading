package by.epamtc.entity;

public class EditData {

    private int diagonalIndex;

    private int mutableIndex;

    private boolean rowMutable;

    public int getDiagonalIndex() {
        return diagonalIndex;
    }

    public void setDiagonalIndex(int diagonalIndex) {
        this.diagonalIndex = diagonalIndex;
    }

    public int getMutableIndex() {
        return mutableIndex;
    }

    public void setMutableIndex(int mutableIndex) {
        this.mutableIndex = mutableIndex;
    }

    public boolean isRowMutable() {
        return rowMutable;
    }

    public void setRowMutable(boolean rowMutable) {
        this.rowMutable = rowMutable;
    }
}
