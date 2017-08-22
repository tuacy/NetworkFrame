package com.tuacy.retrofitdemo;


import com.tuacy.retrofitdemo.request.GetRequest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppRetrofitApi {

	@GET("db")
	Call<GetRequest> getAllList();

}
