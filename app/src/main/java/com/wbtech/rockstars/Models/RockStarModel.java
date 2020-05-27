package com.wbtech.rockstars.Models;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

public class RockStarModel {


    @Json(name = "first_name")
    String firstName;
    @Json(name = "last_name")
    String lastName;

    @Json(name = "status")
    String status;
    @Json(name = "picture_url")
    String pictureUrl;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
