package com.example.lightbulb;

public class LightContextState {

    private int id;
    private String status;
    private int level;
    private float roomId;

    public LightContextState(int id, String status, int level, float roomId) {
        super();
        this.id = id;
        this.status = status;
        this.level = level;
        this.roomId = roomId;
    }

    public int getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public int getLevel() {
        return this.level;
    }

    public float getRoomId() {
        return this.roomId;
    }
}