package com.wbtech.rockstars.Network;

import com.wbtech.rockstars.Models.RockStarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RockStarsApiService {


    @GET(value = "5ecbd116bbaf1f258946ce22")
    Call<ResponseModel<List<RockStarModel>>> getRockStars();
}
