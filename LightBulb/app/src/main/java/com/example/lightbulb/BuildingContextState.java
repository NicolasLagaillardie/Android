package com.example.lightbulb;

public class BuildingContextState {

    private int id;
    private String name;

    public BuildingContextState(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
