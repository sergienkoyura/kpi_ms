package com.serhiienko.oop;

public class Channel {

    private int state = 0;
    private double tnext = Double.MAX_VALUE;

    public int getState() {
        return state;
    }

    public double getTnext() {
        return tnext;
    }

    public void setFree() {
        state = 0;
        tnext = Double.MAX_VALUE;
    }

    public void setBusy(double tnext) {
        state = 1;
        this.tnext = tnext;
    }
}
