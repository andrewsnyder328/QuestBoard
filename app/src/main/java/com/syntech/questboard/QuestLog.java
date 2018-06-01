package com.syntech.questboard;

import java.util.List;

public class QuestLog {

    private int id;
    private String name;
    private String image;
    private String climate;
    private int population;
    private List<Quest> quests;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getClimate() {
        return climate;
    }

    public int getPopulation() {
        return population;
    }

    public List<Quest> getQuests() {
        return quests;
    }
}