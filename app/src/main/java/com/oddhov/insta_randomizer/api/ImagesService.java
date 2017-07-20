package com.oddhov.insta_randomizer.api;


import com.oddhov.insta_randomizer.models.Image;
import com.oddhov.insta_randomizer.models.Images;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagesService {
    private static final int DEFAULT_TIMEOUT = 10;
    private ImagesApi mImagesApi;

    public ImagesService() {
        createImagesService();
    }

    private void createImagesService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ImagesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mImagesApi = retrofit.create(ImagesApi.class);
    }

    public void getImageForQuery(Observer<Image> subscriber, String query) {
        mImagesApi.getCreativeImageForPhrase(ImagesApi.API_KEY_VALUE, query)
                .flatMap(new Function<Images, Observable<Image>>() {

                    @Override
                    public Observable<Image> apply(@NonNull Images images) throws Exception {
                        return Observable.just(images.getImages().get(new Random().nextInt(images.getImages().size() + 1)));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
