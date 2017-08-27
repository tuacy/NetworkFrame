package com.tuacy.okhttpdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tuacy.okhttpdemo.base.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {

	}

	private void initEvent() {
		findViewById(R.id.button_get).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Request request = new Request.Builder()
					.url("http://192.168.11.100:3033/books")
					.get()
					.build();

				mClient.newCall(request).enqueue(new Callback(){
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {

					}

					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

					}
				});

			}
		});
	}

	private void initData() {

	}
}
