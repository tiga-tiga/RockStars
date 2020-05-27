package com.wbtech.rockstars.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.wbtech.rockstars.Commons.BaseActivity;
import com.wbtech.rockstars.Managers.AppManager;
import com.wbtech.rockstars.R;

public class SplashScreenActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        showProgressDialog("Loading...");

        //retrieve rockStars from API
        AppManager.getInstance().getRockStars(this, new AppManager.LoadRockStarsListener() {
            @Override
            public void onComplete() {
                hideProgressDialog();
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onFailure() {
                showAlertDialog(SplashScreenActivity.this, "Something went wrong!", new AlertDialogListener() {
                    @Override
                    public void onConfirm() {
                        finish();
                    }
                });
            }
        });
    }


}
