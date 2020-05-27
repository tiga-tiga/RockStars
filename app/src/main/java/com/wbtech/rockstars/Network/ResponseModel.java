package com.wbtech.rockstars.Network;

import com.squareup.moshi.Json;

public class ResponseModel<T> {


    @Json(name = "team")
    T data;


    public T getData() {
        return data;
    }

}
