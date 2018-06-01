package com.syntech.questboard;

public class Quest {

    private int id;
    private String name;
    private String description;
    private String image;
    private Giver giver;

    public int getId() {
        return id;
    }

    public String getQuestName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Giver getGiver() {
        return giver;
    }

}