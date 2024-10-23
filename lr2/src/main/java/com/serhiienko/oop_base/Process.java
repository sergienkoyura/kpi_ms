package com.serhiienko.oop_base;


public class Process extends Element {
    private int queue, maxqueue, failure;
    private double meanQueue, meanLoad;

    public Process(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        setTnext(Double.MAX_VALUE);
    }

    @Override
    public void inAct() {
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        // task 3
        Element next = getNextElement();
        if (next != null)
            next.inAct();

        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);

        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        }
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.printf("failure = %d, queue = %d%n",
                this.getFailure(), this.getQueue());
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
        // task 2
        meanLoad += getState() * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    public double getMeanLoad() {
        return meanLoad;
    }
}