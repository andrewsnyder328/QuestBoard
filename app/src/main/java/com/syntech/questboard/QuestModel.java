package com.syntech.questboard;

public class QuestModel {

    private int id;
    private String name;
    private String description;
    private String image;
    private GiverModel giver;

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

    public GiverModel getGiver() {
        return giver;
    }

}