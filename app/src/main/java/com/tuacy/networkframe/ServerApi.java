package com.tuacy.networkframe;


import com.tuacy.networkframe.bean.BookBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import retrofit2.http.Url;
import rx.Observable;

public interface ServerApi {

	@GET("books")
	Observable<List<BookBean>> getAllBook();

	@GET("books/{id}")
	Observable<BookBean> getBookByPath(@Path("id") int id);

	@GET("books")
	Observable<List<BookBean>> getBookByQuery(@Query("id") int id);

	@GET("books")
	Observable<List<BookBean>> getBookByQuery(@Query("id") int id, @Query("title") String title);

	@GET("books")
	Observable<List<BookBean>> getBookByQueryName(@Query("id") int id, @QueryName String title);

	@GET
	Observable<List<BookBean>> getBookByUrl(@Url String relateUrl);

	@POST("books")
	Observable<BookBean> addBookPost(@Body BookBean book);

	@Headers({"test-header: header",
			  "test-header: vue"})
	@FormUrlEncoded
	@POST("books")
	Observable<BookBean> addBookForm(@Field("title") String title, @Field("author") String author);


}
