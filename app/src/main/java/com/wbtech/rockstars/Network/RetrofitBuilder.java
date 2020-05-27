package com.wbtech.rockstars.Network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {

    private static final String BASE_URL= "https://api.jsonbin.io/b/";


    private static OkHttpClient buildClient(final Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder()
                        .addHeader("Accept", "application.json")
                        .addHeader("Content-Type", "application.json")
                        .addHeader("X-Requested-With", "XMLHttpRequest");

                request = builder.build();

                return chain.proceed(request);
            }
        });
        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .build();

    }

    public static <T> T createService(Class<T> service, Context context){
        OkHttpClient client = buildClient(context);
        Retrofit retrofit = buildRetrofit(client);
        return retrofit.create(service);
    }


}
