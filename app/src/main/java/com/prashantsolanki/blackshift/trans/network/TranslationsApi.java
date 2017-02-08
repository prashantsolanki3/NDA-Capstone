package com.prashantsolanki.blackshift.trans.network;

import com.prashantsolanki.blackshift.trans.model.Result;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by prsso on 06-02-2017.
 */

public interface TranslationsApi {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.funtranslations.com/translate/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("{speech}.json")
    Call<Result> translate(@Path("speech") String speech, @Query("text") String input);
}
