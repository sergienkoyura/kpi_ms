package com.serhiienko.oop_base;


import java.util.ArrayList;
import java.util.List;

public class SimModel {
    public static void main(String[] args) {
//        generateTask1().simulate(1000.0);
//        generateTask3().simulate(1000.0);
    }

    static Model generateTask1() {
        Create c = new Create(2.0);
        Process p = new Process(1.0);
        System.out.println("id0 = " + c.getId() + " id1=" + p.getId());

        c.setNextElement(p);
        p.setMaxqueue(5);

        c.setName("CREATOR");
        p.setName("PROCESSOR");

        c.setDistribution("exp");
        p.setDistribution("exp");

        return new Model(new ArrayList<>(List.of(c, p)));
    }

    static Model generateTask3() {
        Create c = new Create(2);
        Process p1 = new Process(1);
        Process p2 = new Process(1.5);
        Process p3 = new Process(1.5);
        System.out.printf("creator = %d%np1 = %d, p2 = %d, p3 = %d%n", c.getId(), p1.getId(), p2.getId(), p3.getId());

        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);

        p1.setMaxqueue(1);
        p2.setMaxqueue(3);
        p3.setMaxqueue(3);

        c.setName("CREATOR");
        p1.setName("PROCESSOR_1");
        p2.setName("PROCESSOR_2");
        p3.setName("PROCESSOR_3");

        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");

        return new Model(new ArrayList<>(List.of(c, p1, p2, p3)));

    }
}