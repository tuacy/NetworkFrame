package com.tuacy.networkframe.library.base;


import android.support.annotation.NonNull;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 网络请求的request Retrofit注解不好表达的一些参数
 */
public abstract class ProtocolsBaseRequest<T> {

	/**
	 * base url
	 */
	protected String                    mBaseUrl          = null;
	/**
	 * connect time out
	 */
	protected long                      mConnectTimeout   = 10;
	/**
	 * read time out
	 */
	protected long                      mReadTimeout      = 10;
	/**
	 * write time out
	 */
	protected long                      mWriteTimeout     = 10;
	/**
	 * request tag
	 */
	protected Object                    mTag              = null;
	/**
	 * converter factory
	 */
	protected Converter.Factory         mFactory          = GsonConverterFactory.create();
	/**
	 * network all type {@link ProtocolsNetworkAllowType}
	 */
	protected ProtocolsNetworkAllowType mNetworkAllowType = ProtocolsNetworkAllowType.ALLOW_NETWORK_ALL;

	public ProtocolsBaseRequest(@NonNull String baseUrl) {
		mBaseUrl = baseUrl;
	}

	/**
	 * get base url
	 *
	 * @return base url
	 */
	public String getBaseUrl() {
		return mBaseUrl;
	}

	/**
	 * set base url
	 *
	 * @param baseUrl base url
	 */
	public void setBaseUrl(String baseUrl) {
		mBaseUrl = baseUrl;
	}

	/**
	 * get connect time out(unit is second)
	 *
	 * @return connect time out
	 */
	public long getConnectTimeout() {
		return mConnectTimeout;
	}

	/**
	 * get connect time out
	 *
	 * @param connectTimeout connect time out
	 */
	public void setConnectTimeout(long connectTimeout) {
		mConnectTimeout = connectTimeout;
	}

	/**
	 * get read time out (unit is second)
	 *
	 * @return read time out
	 */
	public long getReadTimeout() {
		return mReadTimeout;
	}

	/**
	 * set read time out
	 *
	 * @param readTimeout read time out
	 */
	public void setReadTimeout(long readTimeout) {
		mReadTimeout = readTimeout;
	}

	/**
	 * get write time out (unit is second)
	 *
	 * @return write time out
	 */
	public long getWriteTimeout() {
		return mWriteTimeout;
	}

	/**
	 * set write time out (unit is second)
	 *
	 * @param writeTimeout write time out
	 */
	public void setWriteTimeout(long writeTimeout) {
		mWriteTimeout = writeTimeout;
	}

	/**
	 * get request tag
	 *
	 * @return request tag
	 */
	public Object getTag() {
		return mTag;
	}

	/**
	 * set request tag
	 *
	 * @param tag request tag
	 */
	public void setTag(Object tag) {
		mTag = tag;
	}

	/**
	 * 设置数据转换的factory
	 *
	 * @param factory factory
	 */
	public void setConvertFactory(Converter.Factory factory) {
		mFactory = factory;
	}

	/**
	 * 获取数据转换的factory
	 *
	 * @return factory
	 */
	public Converter.Factory getConvertFactory() {
		return mFactory;
	}

	/**
	 * 设置网络访问等级
	 *
	 * @param type 网络访问等级
	 */
	public void setNetworkAllowType(ProtocolsNetworkAllowType type) {
		mNetworkAllowType = type;
	}

	/**
	 * 获取网络访问等级
	 *
	 * @return 网络访问等级
	 */
	public ProtocolsNetworkAllowType getNetworkAllowType() {
		return mNetworkAllowType;
	}

	/**
	 * 获取request对应的Observable
	 *
	 * @param retrofit Retrofit
	 * @return Observable
	 */
	public abstract Observable<T> getObservable(Retrofit retrofit);

}
