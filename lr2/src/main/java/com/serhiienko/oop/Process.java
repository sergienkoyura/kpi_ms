package com.serhiienko.oop;


import java.util.ArrayList;
import java.util.List;

public class Process extends Element {
    private int queue, maxqueue, failure, channelSize;
    private double meanQueue, meanLoad;
    private ArrayList<Channel> channels;

    public Process(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        setTnext(Double.MAX_VALUE);
    }

    public Process(double delay, int channelSize) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        setTnext(Double.MAX_VALUE);
        this.channelSize = channelSize;

        channels = new ArrayList<>();
        for (int i = 0; i < channelSize; i++) {
            channels.add(new Channel());
        }
    }

    @Override
    public void inAct() {
        boolean taken = false;
        for (Channel sp : channels) {
            if (sp.getState() == 0) {
                sp.setBusy(super.getTcurr() + super.getDelay());
                taken = true;
                break;
            }
        }

        setBusy();

        if (!taken) {
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
        if (next != null) {
            System.out.println("Into Processor " + next.getId());
            next.inAct();
        }

        List<Channel> freeChannels = channels.stream().filter((sp) -> {
            if (sp.getTnext() == getTcurr()) {
                super.outAct();
                sp.setFree();
            }
            return sp.getState() == 0;
        }).toList();


        super.setTnext(Double.MAX_VALUE);
        if (freeChannels.size() == channelSize) {
            super.setState(0);
        } else {
            setBusy();
        }

        for (Channel sp : freeChannels) {
            if (getQueue() > 0) {
                setQueue(getQueue() - 1);

                sp.setBusy(super.getTcurr() + super.getDelay());

                setBusy();
            }
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

    public int getChannelSize() {
        return channels.size();
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.printf("failure = %d, queue = %d, channelsBusy = %d%n",
                this.getFailure(), this.getQueue(), channels.stream().filter((el) -> el.getState() == 1).count());
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

    public void setBusy() {
        super.setState(1);
        for (Channel sp : channels) {
            if (sp.getTnext() < super.getTnext()) {
                super.setTnext(sp.getTnext());
            }
        }
    }
}