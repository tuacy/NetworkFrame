package com.tuacy.retrofitdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tuacy.retrofitdemo.AppRetrofitApi;
import com.tuacy.retrofitdemo.application.AppApplication;


public class BaseActivity extends AppCompatActivity {

	protected AppRetrofitApi mNetworkApi;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNetworkApi = ((AppApplication) getApplication()).getNetworkApi();
	}
}
