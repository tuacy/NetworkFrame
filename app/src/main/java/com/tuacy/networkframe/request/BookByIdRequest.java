package com.tuacy.networkframe.request;


import com.tuacy.networkframe.ServerApi;
import com.tuacy.networkframe.bean.BookBean;
import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;

import retrofit2.Retrofit;
import rx.Observable;


public class BookByIdRequest extends ProtocolsBaseRequest<BookBean> {

	private int mId;

	public BookByIdRequest(int id) {
		super("http://192.168.5.14:3033/");
		mId = id;
	}

	public void setId(int id) {
		mId = id;
	}

	@Override
	public Observable<BookBean> getObservable(Retrofit retrofit) {
		return retrofit.create(ServerApi.class).getBookByPath(mId);
	}
}
