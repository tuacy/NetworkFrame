package com.tuacy.retrofitdemo;


import com.tuacy.retrofitdemo.bean.BookBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

public interface AppRetrofitApi {

	@GET("books")
	Call<List<BookBean>> getAllBook();

	@GET("books/{id}")
	Call<BookBean> getBookByPath(@Path("id") int id);

	@GET("books")
	Call<List<BookBean>> getBookByQuery(@Query("id") int id);

	@GET("books")
	Call<List<BookBean>> getBookByQuery(@Query("id") int id, @Query("title") String title);

	@GET("books")
	Call<List<BookBean>> getBookByQueryName(@Query("id") int id, @QueryName String title);

	@GET
	Call<List<BookBean>> getBookByUrl(@Url String relateUrl);

	@POST("books")
	Call<BookBean> getBookPost(@Body BookBean book);


}
