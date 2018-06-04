package com.syntech.questboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {

    @GET("api/v1/kingdoms")
    Call<List<KingdomModel>> getKingdoms();

    @GET("api/v1/kingdoms/{id}")
    Call<QuestLogModel> getQuests(
            @Path("id") int id
    );

    @GET("api/v1/characters/{id}")
    Call<CharacterModel> getCharacter(
            @Path("id") int id
    );

    @POST("api/v1/subscribe")
    @FormUrlEncoded
    Call<Message> registerEmail(@Field("email") String email);

}
