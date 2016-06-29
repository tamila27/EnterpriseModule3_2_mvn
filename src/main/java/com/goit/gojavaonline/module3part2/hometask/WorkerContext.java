package com.goit.gojavaonline.module3part2.hometask;

/**
 * Created by tamila on 6/18/16.
 */
public class WorkerContext {
    private int[] array;
    private int startIndex;
    private int endIndex;
    private PhaserOwner phaserOwner;

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setPhaserOwner(PhaserOwner phaserOwner) {
        this.phaserOwner = phaserOwner;
    }

    public boolean isReady(){
        return array != null;
    }

    public void arriveAndAwaitAdvance(){
        phaserOwner.arriveAndAwaitAdvance();
    }
}
