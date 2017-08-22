package com.tuacy.retrofitdemo.application;

import android.app.Application;

import com.tuacy.retrofitdemo.AppRetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppApplication extends Application {

	private AppRetrofitApi mNetworkApi;

	@Override
	public void onCreate() {
		super.onCreate();
		initRetrofit();
	}

	private void initRetrofit() {
		Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
												  .baseUrl("http://192.168.5.14:3003/")
												  .build();
		mNetworkApi = retrofit.create(AppRetrofitApi.class);
	}

	public AppRetrofitApi getNetworkApi() {
		return mNetworkApi;
	}
}
