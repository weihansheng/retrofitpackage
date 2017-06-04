package com.johan007.retrofitpackage;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Johan007 on 2017/6/4.
 */

public class NetRequest {
    public final static String TEMP_UPLOAD_IMAGE_PREFIX = "tmp";
    public final static int IMAGE_SIZE_THRESHOLD = 400 * 1024;//400K
    public final static String BASE_URL = "https://api.github.com";
    public final static String LINE_UP_URL = "https://api.github.com/";
    public final static String AFFAIR_URL = "https://api.github.com/";
    public static APIClient APIInstance;
    public static APIClient APIInstance2;
    public static Cache cache;

    static {
        try {
            cache = new Cache(MyApplication.context.getCacheDir(), 1 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .connectTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create());
        APIInstance = builder.baseUrl(BASE_URL).build().create(APIClient.class);
        APIInstance2 = builder.baseUrl(AFFAIR_URL).build().create(APIClient.class);

    }
}
