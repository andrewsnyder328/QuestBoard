package com.syntech.questboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {

    @GET("api/v1/kingdoms")
    Call<List<Kingdom>> getKingdoms();

    @GET("api/v1/kingdoms/{id}")
    Call<QuestLog> getQuests(
            @Path("id") int id
    );

    @GET("api/v1/characters/{id}")
    Call<Character> getCharacter(
            @Path("id") int id
    );

}
