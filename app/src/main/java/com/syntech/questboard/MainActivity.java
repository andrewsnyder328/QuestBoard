package com.syntech.questboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://challenge2015.myriadapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Kingdom>> call = request.getKingdoms();
        call.enqueue(new Callback<List<Kingdom>>() {
            @Override
            public void onResponse(Call<List<Kingdom>> call, Response<List<Kingdom>> response) {

                List<Kingdom> kingdoms = response.body();
                adapter = new DataAdapter(kingdoms);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Kingdom>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
