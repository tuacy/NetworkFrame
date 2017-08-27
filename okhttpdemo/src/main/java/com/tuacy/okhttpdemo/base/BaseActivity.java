package com.tuacy.okhttpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tuacy.okhttpdemo.application.AppApplication;

import okhttp3.OkHttpClient;


public class BaseActivity extends AppCompatActivity {

	protected OkHttpClient mClient;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClient = ((AppApplication)getApplication()).getOkHttpClient();
	}
}
