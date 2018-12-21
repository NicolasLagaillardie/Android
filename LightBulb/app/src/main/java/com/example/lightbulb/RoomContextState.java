package com.example.lightbulb;

public class RoomContextState {

    private int id;
    private String name;
    private int floor;
    private int buildingId;

    public RoomContextState(int id, String name, int floor, int buildingId) {
        super();
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.buildingId = buildingId;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getFloor() {
        return this.floor;
    }

    public int getBuildingId() {
        return this.buildingId;
    }
}