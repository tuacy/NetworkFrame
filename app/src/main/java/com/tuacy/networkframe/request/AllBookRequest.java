package com.tuacy.networkframe.request;


import com.tuacy.networkframe.ServerApi;
import com.tuacy.networkframe.bean.BookBean;
import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class AllBookRequest extends ProtocolsBaseRequest<List<BookBean>> {

	public AllBookRequest() {
		super("http://192.168.5.14:3033/");
	}

	@Override
	public Observable<List<BookBean>> getObservable(Retrofit retrofit) {
		return retrofit.create(ServerApi.class).getAllBook();
	}
}
