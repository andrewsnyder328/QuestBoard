package com.syntech.questboard;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private static ViewPager viewPager;
    private List<String> listItem = new ArrayList<>();
    private static QuestLogModel questLog;
    private FragmentAdapter fragmentAdapter;
    private int id;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questLog = new QuestLogModel();
        setContentView(R.layout.activity_quests);
        setActionBarTitle("");

        indicator = (LinearLayout) findViewById(R.id.indicators);
        viewPager = (ViewPager) findViewById(R.id.viewPager_itemList);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        loadJSON();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setData(){
        listItem.add(questLog.getName());
        for (int i = 0; i < questLog.getQuests().size(); i++){
            listItem.add(questLog.getQuests().get(i).getQuestName());
        }

        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), questLog);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(questLog.getQuests().size());
        viewPager.setOnPageChangeListener(this);
        setUiPageViewController();
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://challenge2015.myriadapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<QuestLogModel> call = request.getQuests(id);
        call.enqueue(new Callback<QuestLogModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<QuestLogModel> call, Response<QuestLogModel> response) {

                questLog = response.body();
                setData();
                setActionBarTitle(questLog.getName());
            }

            @Override
            public void onFailure(Call<QuestLogModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUiPageViewController(){
        mDotCount = fragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for(int i=0; i<mDotCount; i++){
            mDots[i] = new LinearLayout(this);
            //quest icon made by Freepik from www.flaticon.com
            mDots[i].setBackgroundResource(R.drawable.ic_quest);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4,0,4,0);
            indicator.addView(mDots[i],params);
            mDots[i].getLayoutParams().height = 100;
            mDots[i].getLayoutParams().width = 100;
            mDots[i].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
            final int finalI = i;
            mDots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollTo(finalI);
                }
            });
        }

        //castle icon made by Freepik from www.flaticon.com
        mDots[0].setBackgroundResource(R.drawable.ic_castle);
        mDots[0].setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        mDots[0].getLayoutParams().height = 150;
        mDots[0].getLayoutParams().width = 150;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPageSelected(int position) {
        for (int i=0; i<mDotCount; i++){
            mDots[i].setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
        }
        mDots[position].setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        setActionBarTitle(listItem.get(position));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void scrollTo(final int i){
        viewPager.setCurrentItem(i, true);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public static List<QuestModel> getQuests(){
        return questLog.getQuests();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
