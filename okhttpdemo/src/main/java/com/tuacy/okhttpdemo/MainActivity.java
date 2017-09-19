package com.tuacy.okhttpdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.tuacy.okhttpdemo.base.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

				OkHttpClient client = new OkHttpClient();

				Request request = new Request.Builder()
					.url("http://192.168.5.14:3033/books?id=1")
					.get()
					.build();

				client.newCall(request).enqueue(new Callback(){
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {

					}

					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
						//TODO:response.body()
					}
				});

			}
		});
	}

	private void initData() {
	}
}
