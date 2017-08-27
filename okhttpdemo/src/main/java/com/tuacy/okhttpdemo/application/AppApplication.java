package com.tuacy.okhttpdemo.application;


import android.app.Application;

import okhttp3.OkHttpClient;

public class AppApplication extends Application {

	private OkHttpClient mClient = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mClient = new OkHttpClient();
	}

	public OkHttpClient getOkHttpClient() {
		return mClient;
	}
}
