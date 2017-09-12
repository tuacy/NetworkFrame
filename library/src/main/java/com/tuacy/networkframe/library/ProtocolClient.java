package com.tuacy.networkframe.library;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ProtocolClient {

	private static long    CONNECT_TIMEOUT = 20;
	private static long    WRITE_TIMEOUT   = 10;
	private static long    READ_TIMEOUT    = 30;
	private static boolean RE_CONNECT      = true;

	/**
	 * 设置默认的connect time out(单位秒),application中设置
	 *
	 * @param timeout time out
	 */
	public static void setCommonConnectTimeout(long timeout) {
		CONNECT_TIMEOUT = timeout;
	}

	/**
	 * 设置默认的write time out(单位秒),application中设置
	 *
	 * @param timeout time out
	 */
	public static void setCommonWriteTimeout(long timeout) {
		WRITE_TIMEOUT = timeout;
	}

	/**
	 * 设置是否重连
	 *
	 * @param retry 是否重连
	 */
	public static void setRetryOnConnection(boolean retry) {
		RE_CONNECT = retry;
	}

	/**
	 * 设置默认的read time out(单位秒),application中设置
	 *
	 * @param timeout time out
	 */
	public static void setCommonReadTimeout(long timeout) {
		READ_TIMEOUT = timeout;
	}

	private OkHttpClient mOkHttpClient = null;

	/**
	 * 构造函数
	 */
	private ProtocolClient() {
		mOkHttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
												  .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
												  .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
												  .retryOnConnectionFailure(RE_CONNECT)
												  .addNetworkInterceptor(
													  new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
												  .build();
	}
}
