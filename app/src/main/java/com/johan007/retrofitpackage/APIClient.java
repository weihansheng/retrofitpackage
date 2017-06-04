package com.johan007.retrofitpackage;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Johan007 on 2017/6/2.
 */

public interface APIClient {
    @GET("repos/square/retrofit/contributors")
    Observable<List<ObjectEntity>> contributorsBySimpleGetCall();
}
