package com.tuacy.networkframe.library;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;
import com.tuacy.networkframe.library.callback.ProtocolsBaseCallback;
import com.tuacy.networkframe.library.cookie.ProtocolsCookies;
import com.tuacy.networkframe.library.exception.ProtocolsError;
import com.tuacy.networkframe.library.exception.ProtocolsException;
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
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class ProtocolsClient {


	/**
	 * 全局就一个基础的OkHttpClient
	 */
	private        OkHttpClient    mOkHttpClient = null;
	/**
	 * 单利模式
	 */
	private static ProtocolsClient mInstance     = null;

	/**
	 * 构造函数
	 */
	private ProtocolsClient() {
		mOkHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
												  .cookieJar(new ProtocolsCookies())
												  .addNetworkInterceptor(
													  new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
												  .build();

	}

	/**
	 * 单利模式
	 */
	public static ProtocolsClient getInstance() {
		if (mInstance == null) {
			synchronized (ProtocolsClient.class) {
				if (mInstance == null) {
					mInstance = new ProtocolsClient();
				}
			}
		}
		return mInstance;
	}


	/**
	 * 统一管理异常
	 */
	private ProtocolsException transformerException(Throwable e) {
		ProtocolsException exception = new ProtocolsException(e, ProtocolsError.ERROR_UNKNOWN);
		if (e instanceof HttpException) {
			//网络异常
			exception.setErrorCode(ProtocolsError.ERROR_HTTP);
		} else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
			//链接异常
			exception.setErrorCode(ProtocolsError.ERROR_TIMEOUT);
		} else if (e instanceof JSONException) {
			exception.setErrorCode(ProtocolsError.ERROR_DATA_FORMAT);
		} else if (e instanceof UnknownHostException) {
			//无法解析该域名异常
			exception.setErrorCode(ProtocolsError.ERROR_HOST);
		} else {
			//未知异常
			exception.setErrorCode(ProtocolsError.ERROR_UNKNOWN);
		}
		return exception;
	}

	/**
	 * 获取request经过处理之后的Observable
	 *
	 * @param request request
	 * @param <T>     类型
	 * @return Observable<T>
	 */
	private <T> Observable<T> getProtocolsRequestObservable(ProtocolsBaseRequest<T> request) {
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

		Func1<Throwable, Observable<T>> mErrorResume = new Func1<Throwable, Observable<T>>() {
			@Override
			public Observable<T> call(Throwable throwable) {
				return Observable.error(transformerException(throwable));
			}
		};

		return request.getObservable(retrofit)
					  .retryWhen(new RetryNetworkException())
					  .onErrorResumeNext(mErrorResume)
					  .subscribeOn(Schedulers.io())
					  .unsubscribeOn(Schedulers.io())
					  .observeOn(AndroidSchedulers.mainThread());

	}

	private  <T, V, Y> Observable<Y> getProtocolsRequestsObservable(ProtocolsBaseRequest<T> requestOne, ProtocolsBaseRequest<V> requestTwo) {

		Func1<Throwable, Observable<Y>> mErrorResume = new Func1<Throwable, Observable<Y>>() {
			@Override
			public Observable<Y> call(Throwable throwable) {
				return Observable.error(transformerException(throwable));
			}
		};

		Observable<T> observableOne = getProtocolsRequestObservable(requestOne);
		Observable<V> observableTwo = getProtocolsRequestObservable(requestTwo);
		return Observable.zip(observableOne, observableTwo, new Func2<T, V, Y>() {
			@Override
			public Y call(T t, V v) {
				return new Y(jsonObject, jsonElements);
			}
		})
						 .retryWhen(new RetryNetworkException())
						 .onErrorResumeNext(mErrorResume)
						 .subscribeOn(Schedulers.io())
						 .unsubscribeOn(Schedulers.io())
						 .observeOn(AndroidSchedulers.mainThread());
	}

	/**
	 * Observable<T>
	 *
	 * @param request              request
	 * @param lifecycleTransformer transformer
	 * @param <T>                  类型
	 * @return Observable<T>
	 */
	private <T> Observable<T> getProtocolsRequestObservable(ProtocolsBaseRequest<T> request, LifecycleTransformer<T> lifecycleTransformer) {
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

		Func1<Throwable, Observable<T>> mErrorResume = new Func1<Throwable, Observable<T>>() {
			@Override
			public Observable<T> call(Throwable throwable) {
				return Observable.error(transformerException(throwable));
			}
		};

		return request.getObservable(retrofit)
					  .retryWhen(new RetryNetworkException())
					  .onErrorResumeNext(mErrorResume)
					  .compose(lifecycleTransformer)
					  .subscribeOn(Schedulers.io())
					  .unsubscribeOn(Schedulers.io())
					  .observeOn(AndroidSchedulers.mainThread());

	}

	/**
	 * 网络请求
	 *
	 * @param context              context
	 * @param request              request
	 * @param callback             callback
	 * @param lifecycleTransformer transformer
	 * @param <T>                  类型
	 */
	public <T> void onProtocolsRequest(Context context,
									   ProtocolsBaseRequest<T> request,
									   ProtocolsBaseCallback<T> callback,
									   LifecycleTransformer<T> lifecycleTransformer) {
		getProtocolsRequestObservable(request, lifecycleTransformer).subscribe(new ProtocolsSubscriber<>(context, request, callback));
	}

	/**
	 * 网络请求
	 *
	 * @param context  context
	 * @param request  request
	 * @param callback callback
	 * @param <T>      类型
	 */
	public <T> void onProtocolsRequest(Context context, ProtocolsBaseRequest<T> request, ProtocolsBaseCallback<T> callback) {
		getProtocolsRequestObservable(request).subscribe(new ProtocolsSubscriber<>(context, request, callback));
	}
}
