package com.serhiienko.base;

public class SimSimple {
    public static void main(String[] args) {
        Model model = new Model(2,1,5);
        model.simulate(1000);
    }
}