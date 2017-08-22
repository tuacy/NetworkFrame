package com.tuacy.retrofitdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.tuacy.retrofitdemo.base.BaseActivity;
import com.tuacy.retrofitdemo.request.GetRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
		findViewById(R.id.button_get_no_parameter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<GetRequest> requestCall = mNetworkApi.getAllList();
				requestCall.enqueue(new Callback<GetRequest>() {
					@Override
					public void onResponse(@NonNull Call<GetRequest> call, @NonNull Response<GetRequest> response) {
						GetRequest body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<GetRequest> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_has_parameter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	private void initData() {

	}

}
