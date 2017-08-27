package com.tuacy.retrofitdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.tuacy.retrofitdemo.base.BaseActivity;
import com.tuacy.retrofitdemo.bean.BookBean;

import java.util.List;

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
		findViewById(R.id.button_get_all_book).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<List<BookBean>> requestCall = mNetworkApi.getAllBook();
				requestCall.enqueue(new Callback<List<BookBean>>() {
					@Override
					public void onResponse(@NonNull Call<List<BookBean>> call, @NonNull Response<List<BookBean>> response) {
						List<BookBean> body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<List<BookBean>> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_path).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<BookBean> requestCall = mNetworkApi.getBookByPath(0);
				requestCall.enqueue(new Callback<BookBean>() {
					@Override
					public void onResponse(@NonNull Call<BookBean> call, @NonNull Response<BookBean> response) {
						BookBean body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<BookBean> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_query).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<List<BookBean>> requestCall = mNetworkApi.getBookByQuery(0);
				requestCall.enqueue(new Callback<List<BookBean>>() {
					@Override
					public void onResponse(@NonNull Call<List<BookBean>> call, @NonNull Response<List<BookBean>> response) {
						List<BookBean> body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<List<BookBean>> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_query_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<List<BookBean>> requestCall = mNetworkApi.getBookByQuery(0, "android");
				requestCall.enqueue(new Callback<List<BookBean>>() {
					@Override
					public void onResponse(@NonNull Call<List<BookBean>> call, @NonNull Response<List<BookBean>> response) {
						List<BookBean> body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<List<BookBean>> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_url).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<List<BookBean>> requestCall = mNetworkApi.getBookByUrl("books");
				requestCall.enqueue(new Callback<List<BookBean>>() {
					@Override
					public void onResponse(@NonNull Call<List<BookBean>> call, @NonNull Response<List<BookBean>> response) {
						List<BookBean> body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<List<BookBean>> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_query_name).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Call<List<BookBean>> requestCall = mNetworkApi.getBookByQueryName(0, "title");
				requestCall.enqueue(new Callback<List<BookBean>>() {
					@Override
					public void onResponse(@NonNull Call<List<BookBean>> call, @NonNull Response<List<BookBean>> response) {
						List<BookBean> body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<List<BookBean>> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_post_body).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BookBean book = new BookBean(0, "html", "html");
				Call<BookBean> requestCall = mNetworkApi.addBookPost(book);
				requestCall.enqueue(new Callback<BookBean>() {
					@Override
					public void onResponse(@NonNull Call<BookBean> call, @NonNull Response<BookBean> response) {
						BookBean body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<BookBean> call, Throwable t) {

					}
				});
			}
		});

		findViewById(R.id.button_get_one_book_post_urlencoded).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BookBean book = new BookBean(0, "html", "html");
				Call<BookBean> requestCall = mNetworkApi.addBookForm("vue", "vue");
				requestCall.enqueue(new Callback<BookBean>() {
					@Override
					public void onResponse(@NonNull Call<BookBean> call, @NonNull Response<BookBean> response) {
						BookBean body = response.body();
						if (body != null) {
							Log.d("tuacy", body.toString());
						}
					}

					@Override
					public void onFailure(@NonNull Call<BookBean> call, Throwable t) {

					}
				});
			}
		});
	}

	private void initData() {

	}

}
