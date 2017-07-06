package com.oddhov.insta_randomizer.api;


import com.oddhov.insta_randomizer.models.Images;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ImagesApi {

    String BASE_URL = "https://api.gettyimages.com/v3/search/images/";
    String API_KEY = "Api-Key";
    String API_KEY_VALUE = "g6wjcu572hmuavtnn6s6v5xj";
    String PHRASE_PARAM = "phrase";

    @GET("creative")
    Observable<Images> getCreativeImageForPhrase(
            @Header(API_KEY) String API_KEY_VALUE,
            @Query(PHRASE_PARAM) String phrase
    );
}
