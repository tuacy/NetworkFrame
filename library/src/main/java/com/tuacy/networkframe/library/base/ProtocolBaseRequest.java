package com.tuacy.networkframe.library.base;


import android.support.annotation.NonNull;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 网络请求的request Retrofit注解不好表达的一些参数
 */
public abstract class ProtocolBaseRequest<T> {

	protected String            mBaseUrl        = null;
	protected long              mConnectTimeout = 20;
	protected long              mReadTimeout    = 20;
	protected long              mWriteTimeout   = 20;
	protected Object            mTag            = null;
	protected Converter.Factory mFactory        = GsonConverterFactory.create();

	public ProtocolBaseRequest(@NonNull String baseUrl) {
		mBaseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return mBaseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		mBaseUrl = baseUrl;
	}

	public long getConnectTimeout() {
		return mConnectTimeout;
	}

	public void setConnectTimeout(long connectTimeout) {
		mConnectTimeout = connectTimeout;
	}

	public long getReadTimeout() {
		return mReadTimeout;
	}

	public void setReadTimeout(long readTimeout) {
		mReadTimeout = readTimeout;
	}

	public long getWriteTimeout() {
		return mWriteTimeout;
	}

	public void setWriteTimeout(long writeTimeout) {
		mWriteTimeout = writeTimeout;
	}

	public Object getTag() {
		return mTag;
	}

	public void setTag(Object tag) {
		mTag = tag;
	}

	public void setConvertFactory(Converter.Factory factory) {
		mFactory = factory;
	}

	public Converter.Factory getConvertFactory() {
		return mFactory;
	}

	public abstract Observable<T> getObservable(Retrofit retrofit);

}
