package ru.bi.stub.model;

import java.util.ArrayList;
import java.util.List;

public class Leak {
    private static final List<Double> list = new ArrayList<>();

    public void add() {
        list.add(Math.random());
    }
}
