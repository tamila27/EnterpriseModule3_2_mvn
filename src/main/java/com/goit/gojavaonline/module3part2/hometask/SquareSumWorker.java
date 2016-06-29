package com.goit.gojavaonline.module3part2.hometask;

/**
 * Created by tamila on 6/18/16.
 */
public class SquareSumWorker implements Runnable {
    private WorkerContext context;
    private long result;

    public SquareSumWorker(WorkerContext workerContext) {
        this.context = workerContext;
        this.result = 0;
    }

    @Override
    public void run() {
        if(context.isReady()) {
            for (int i = context.getStartIndex(); i <= context.getEndIndex(); i++) {
                result += Math.pow(context.getArray()[i], 2);
            }
        }
        context.arriveAndAwaitAdvance();
    }

    public long getResult() {
        return result;
    }
}
