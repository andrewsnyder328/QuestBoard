package com.syntech.questboard;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.master.glideimageview.GlideImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestFragment extends android.support.v4.app.Fragment {

    private TextView questDescription;
    private TextView characterName;
    private TextView characterBio;
    private TextView characterProfession;
    private GlideImageView questImg;
    private GlideImageView characterImg;
    private CharacterModel character;
    private int id;
    private String questImgKey;
    private String questDescriptionKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quest, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questDescription = (TextView) view.findViewById(R.id.quest_description);
        characterName = (TextView) view.findViewById(R.id.character_name);
        characterBio = (TextView) view.findViewById(R.id.character_bio);
        characterProfession = (TextView) view.findViewById(R.id.character_profession);
        questImg = (GlideImageView) view.findViewById(R.id.quest_image);
        characterImg = (GlideImageView) view.findViewById(R.id.character_image);

        id =  getArguments().getInt("id", 0);
        questImgKey = getArguments().getString("imgKey");
        questDescriptionKey = getArguments().getString("descKey");

        questImg.loadImageUrl(questImgKey);
        questDescription.setText(questDescriptionKey);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://challenge2015.myriadapps.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<CharacterModel> call = request.getCharacter(id);
        call.enqueue(new Callback<CharacterModel>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {

                character = response.body();
                characterName.setText(character.getName());
                characterProfession.setText(character.getProfession());
                characterBio.setText(character.getBio());
                characterImg.loadImageUrl(character.getImage());
            }

            @Override
            public void onFailure(Call<CharacterModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }
}