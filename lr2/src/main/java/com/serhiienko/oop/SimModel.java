package com.serhiienko.oop;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimModel {
    public static void main(String[] args) {
        generateTask5().simulate(1000.0);
    }
    static Model generateTask5() {
        Create c = new Create(1);
        Process p1 = new Process(1, 2);
        Process p2 = new Process(1, 2);
        Process p3 = new Process(1, 2);
        System.out.printf("creator = %d%np1 = %d (%d-ch), p2 = %d (%d-ch), p3 = %d (%d-ch)%n",
                c.getId(), p1.getId(), p1.getChannelSize(), p2.getId(), p2.getChannelSize(), p3.getId(), p3.getChannelSize());

        // task 5
//        c.setNextElements(List.of(p1));
//        p1.setNextElements(List.of(p2));
//        p2.setNextElements(List.of(p3));


        // task 6
        c.setNextElements(List.of(p1, p2, p3));
        p1.setNextElements(List.of(p2, p3));
        p2.setNextElements(List.of(p1, p3));
        p3.setNextElements(Arrays.asList(p1, p2, null));

        p1.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);

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