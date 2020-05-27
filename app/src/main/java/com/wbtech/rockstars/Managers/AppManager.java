package com.wbtech.rockstars.Managers;

import android.content.Context;

import com.wbtech.rockstars.Models.RockStarModel;
import com.wbtech.rockstars.Network.ResponseModel;
import com.wbtech.rockstars.Network.RetrofitBuilder;
import com.wbtech.rockstars.Network.RockStarsApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AppManager {

    private List<RockStarModel> rockStarModels;

    private static AppManager INSTANCE = null;

    private AppManager() {
    }

    public static synchronized AppManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppManager();
        }
        return INSTANCE;
    }

    //return rock stars list
    public List<RockStarModel> getRockStarModels() {
        return rockStarModels;
    }


    //retrieve saved rock stars form sharedPreferences
    public List<RockStarModel> getBookMarksList(Context context) {
        List<RockStarModel> bookMarksList = new ArrayList<>();
        Set<String> rockStarNames = PreferencesManager.getInstance(context.getSharedPreferences("prefs", MODE_PRIVATE)).getBookMark();

        for (RockStarModel rockStarModel : rockStarModels) {
            String fullName = String.format("%s %s", rockStarModel.getFirstName(), rockStarModel.getLastName());
            if (rockStarNames.contains(fullName)) {
                bookMarksList.add(rockStarModel);
            }
        }
        return bookMarksList;
    }


    //retrieve rock stars form API
    public void getRockStars(Context context, final LoadRockStarsListener loadRockStarsListener) {
        RockStarsApiService rockStarsApiService = RetrofitBuilder.createService(RockStarsApiService.class, context);
        Call<ResponseModel<List<RockStarModel>>> call = rockStarsApiService.getRockStars();
        call.enqueue(new Callback<ResponseModel<List<RockStarModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<RockStarModel>>> call, Response<ResponseModel<List<RockStarModel>>> response) {
                if (response.isSuccessful()) {
                    rockStarModels = response.body().getData();
                    loadRockStarsListener.onComplete();
                } else {
                    loadRockStarsListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<RockStarModel>>> call, Throwable t) {
                loadRockStarsListener.onFailure();
            }
        });

    }

    //Listener on request complete
    public interface LoadRockStarsListener {
        void onComplete();

        void onFailure();
    }
}
