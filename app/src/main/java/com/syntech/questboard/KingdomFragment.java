package com.syntech.questboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.master.glideimageview.GlideImageView;

import java.util.List;

public class KingdomFragment extends android.support.v4.app.Fragment {

    private TextView kingdom_climate;
    private TextView kingdom_population;
    private GlideImageView kingdom_img;
    private String climate;
    private String image;
    private int population;
    private RecyclerView questList;
    private QuestLogAdapter adapter;
    private List<Quest> quests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kingdom, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kingdom_climate = (TextView) view.findViewById(R.id.kingdom_climate);
        kingdom_img = (GlideImageView) view.findViewById(R.id.quest_image);
        kingdom_population = (TextView) view.findViewById(R.id.kingdom_population);

        questList = (RecyclerView) view.findViewById(R.id.quest_recycler_view);
        questList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        questList.setLayoutManager(layoutManager);

        kingdom_climate.setText(getClimate());
        kingdom_img.loadImageUrl(getImage());
        kingdom_population.setText(String.valueOf(getPopulation()));

        quests = Quests.getQuests();

        adapter = new QuestLogAdapter(quests);
        questList.setAdapter(adapter);

    }

    public String getClimate(){
        return this.climate;
    }

    public void setClimate(String climate){
        this.climate = climate;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public int getPopulation(){
        return this.population;
    }

    public void setPopulation(int population){
        this.population = population;
    }



}