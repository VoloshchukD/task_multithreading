package by.epamtc.entity;

public class EditData {

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
    public String toString() {
        return getClass().getName() +
                "@threadId=" + threadId +
                ", diagonalIndex=" + diagonalIndex +
                ", newElement=" + newElement +
                ", mutableIndex=" + mutableIndex +
                ", rowMutable=" + rowMutable;
    }

}
