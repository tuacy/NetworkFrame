package com.tuacy.networkframe.library;

import android.content.Context;

import com.tuacy.networkframe.library.base.ProtocolBaseRequest;
import com.tuacy.networkframe.library.exception.ProtocolError;
import com.tuacy.networkframe.library.exception.ProtocolException;
import com.tuacy.networkframe.library.exception.RetryNetworkException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProtocolClient {


	/**
	 * 全局就一个基础的OkHttpClient
	 */
	private        OkHttpClient   mOkHttpClient = null;
	/**
	 * 单利模式
	 */
	private static ProtocolClient mInstance     = null;

	/**
	 * 构造函数
	 */
	private ProtocolClient() {
		mOkHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
												  .addNetworkInterceptor(
													  new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
												  .build();

	}

	/**
	 * 单利模式
	 */
	public static ProtocolClient getInstance() {
		if (mInstance == null) {
			synchronized (ProtocolClient.class) {
				if (mInstance == null) {
					mInstance = new ProtocolClient();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 异常处理
	 */
	private Func1<Throwable, Observable<T>> mErrorResume = new Func1<Throwable, Observable<T>>() {
		@Override
		public Observable<T> call(Throwable throwable) {
			return Observable.error(transformerException(throwable));
		}
	};

	/**
	 * 统一管理异常
	 */
	private ProtocolException transformerException(Throwable e) {
		ProtocolException exception = new ProtocolException(e, ProtocolError.ERROR_UNKNOWN);
		if (e instanceof HttpException) {
			/**
			 * 网络异常
			 */
			exception.setErrorCode(ProtocolError.ERROR_HTTP);
		} else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
			/**
			 * 链接异常
			 */
			exception.setErrorCode(ProtocolError.ERROR_CONNECT);
		} else if (e instanceof JSONException) {
			exception.setErrorCode(ProtocolError.ERROR_DATA_FORMAT);
		} else if (e instanceof UnknownHostException) {
			/**
			 * 无法解析该域名异常
			 */
			exception.setErrorCode(ProtocolError.ERROR_HOST);
		} else {
			/**
			 * 未知异常
			 */
			exception.setErrorCode(ProtocolError.ERROR_UNKNOWN);
		}
		return exception;
	}

	public <T> void onProtocolRequest(Context context, ProtocolBaseRequest<T> request/*, LifecycleTransformer<T> lifecycleTransformer*/) {
		OkHttpClient okHttpClient = mOkHttpClient.newBuilder()
												 .connectTimeout(request.getConnectTimeout(), TimeUnit.SECONDS)
												 .readTimeout(request.getReadTimeout(), TimeUnit.SECONDS)
												 .writeTimeout(request.getWriteTimeout(), TimeUnit.SECONDS)
												 .build();
		Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
												  .addConverterFactory(request.getConvertFactory())
												  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
												  .baseUrl(request.getBaseUrl())
												  .build();

		/*rx处理*/
		Observable<T> observable = request.getObservable(retrofit).retryWhen(new RetryNetworkException())//失败后的retry配置
										  .onErrorResumeNext((Func1<Throwable, Observable<T>>)mErrorResume)//异常处理
										  //										  .compose(lifecycleTransformer)//生命周期管理(Activity，Fragment等销毁的时候处理)
										  .subscribeOn(Schedulers.io())
										  .unsubscribeOn(Schedulers.io())//http请求线程
										  .observeOn(AndroidSchedulers.mainThread());//回调线程
	}
}
