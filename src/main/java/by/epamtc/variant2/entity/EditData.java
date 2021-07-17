package by.epamtc.variant2.entity;

import java.io.Serializable;

public class EditData implements Serializable {

    private int threadId;

    private int diagonalIndex;

    private int newElement;

    private int mutableIndex;

    private boolean rowMutable;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getDiagonalIndex() {
        return diagonalIndex;
    }

    public void setDiagonalIndex(int diagonalIndex) {
        this.diagonalIndex = diagonalIndex;
    }

    public int getNewElement() {
        return newElement;
    }

    public void setNewElement(int newElement) {
        this.newElement = newElement;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EditData editData = (EditData) o;
        return threadId == editData.threadId && diagonalIndex == editData.diagonalIndex
                && newElement == editData.newElement && mutableIndex == editData.mutableIndex
                && rowMutable == editData.rowMutable;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + threadId;
        result = 37 * result + diagonalIndex;
        result = 37 * result + newElement;
        result = 37 * result + mutableIndex;
        result = 37 * result + (rowMutable ? 0 : 1);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName()
                + "@threadId=" + threadId +
                ", diagonalIndex=" + diagonalIndex +
                ", newElement=" + newElement +
                ", mutableIndex=" + mutableIndex +
                ", rowMutable=" + rowMutable;
    }

}
