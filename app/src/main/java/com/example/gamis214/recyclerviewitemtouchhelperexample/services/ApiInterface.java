package com.example.gamis214.recyclerviewitemtouchhelperexample.services;

import com.example.gamis214.recyclerviewitemtouchhelperexample.Model.ServicesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gamis214 on 30/10/17.
 */

public interface ApiInterface {

    @GET("/json/menu.json")
    Call<List<ServicesResponse>> getListItems();

}
