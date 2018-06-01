package com.syntech.questboard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
    private Context ctx;
    private QuestLog questLog;
    private List<Character> characters = new ArrayList<>();
    private android.support.v4.app.Fragment[] fragments;

    public FragmentAdapter(Context ctx, FragmentManager fm, QuestLog questLog) {
        super(fm);
        this.ctx = ctx;
        this.questLog = questLog;
        fragments = new android.support.v4.app.Fragment[questLog.getQuests().size()+1];
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        android.support.v4.app.Fragment fragment = null;

        if (position == 0){
            KingdomFragment kingdomFragment = new KingdomFragment();
            kingdomFragment.setClimate(questLog.getClimate());
            kingdomFragment.setImage(questLog.getImage());
            kingdomFragment.setPopulation(questLog.getPopulation());
            fragment = kingdomFragment;
        } else {

            Bundle bundle = new Bundle();
            bundle.putInt("id", questLog.getQuests().get(position-1).getGiver().getId());
            bundle.putString("imgKey", questLog.getQuests().get(position-1).getImage());
            bundle.putString("descKey", questLog.getQuests().get(position-1).getDescription());

            QuestFragment questFragment = new QuestFragment();
            questFragment.setArguments(bundle);
            //questFragment.setDetail(questLog.getQuests().get(position-1).getDescription());
            //questFragment.setDetail(characters.get(position).getName());
            fragment = questFragment;
        }


        if (fragments[position] == null) {
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        if (questLog.getQuests() != null) {
            return questLog.getQuests().size()+1;
        } else {
            return 0;
        }
    }
}