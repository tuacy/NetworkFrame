package com.tuacy.networkframe.library;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;
import com.tuacy.networkframe.library.callback.ProtocolsBaseCallback;
import com.tuacy.networkframe.library.cookie.ProtocolsCookies;
import com.tuacy.networkframe.library.exception.ProtocolsError;
import com.tuacy.networkframe.library.exception.ProtocolsException;
import com.tuacy.networkframe.library.exception.RetryNetworkException;
import com.tuacy.networkframe.library.functions.ProtocolsFlatMapFunc;
import com.tuacy.networkframe.library.functions.ProtocolsZipFunc;
import com.tuacy.networkframe.library.transformer.ProtocolsPermissionTransformer;

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


/**
 * 1. 单个请求 {调用 @link ProtocolsClient protocolsRequest()}
 * 2. 多个请求的结果结合到一起 {@link ProtocolsClient->protocolsRequestZipWith() RxJava zip 操作符实现}
 * 3. 对个请求顺序执行，并且前后有依赖 {@link ProtocolsClient->protocolsRequestFlatMap() RxJava flatMap 操作符实现}
 */
public class ProtocolsClient {


	/**
	 * 全局就一个基础的OkHttpClient,为了充分的利用资源
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
		if (e instanceof ProtocolsException) {
			return (ProtocolsException) e;
		} else {
			ProtocolsException exception = new ProtocolsException(e, ProtocolsError.ERROR_UNKNOWN);
			if (e instanceof HttpException) {
				//网络异常
				exception.setErrorCode(ProtocolsError.ERROR_HTTP);
			} else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
				//链接异常
				exception.setErrorCode(ProtocolsError.ERROR_TIMEOUT);
			} else if (e instanceof JSONException) {
				exception.setErrorCode(ProtocolsError.ERROR_FORMAT);
			} else if (e instanceof UnknownHostException) {
				//无法解析该域名异常
				exception.setErrorCode(ProtocolsError.ERROR_HOST);
			} else {
				//未知异常
				exception.setErrorCode(ProtocolsError.ERROR_UNKNOWN);
			}
			return exception;
		}
	}

	/**
	 * 获取request经过处理之后的Observable
	 *
	 * @param context context
	 * @param request request
	 * @param <T>     类型
	 * @return Observable<P>
	 */
	public <T> Observable<T> getProtocolsRequestObservable(Context context, ProtocolsBaseRequest<T> request) {
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
					  .compose(new ProtocolsPermissionTransformer<>(context, request))
					  .subscribeOn(Schedulers.io())
					  .unsubscribeOn(Schedulers.io())
					  .observeOn(AndroidSchedulers.mainThread());

	}

	/**
	 * Observable<P>
	 *
	 * @param context              context
	 * @param request              request
	 * @param lifecycleTransformer transformer
	 * @param <T>                  类型
	 * @return Observable<P>
	 */
	public <T> Observable<T> getProtocolsRequestObservable(Context context,
														   ProtocolsBaseRequest<T> request,
														   LifecycleTransformer<T> lifecycleTransformer) {
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
					  .compose(new ProtocolsPermissionTransformer<>(context, request))
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
	public <T> void protocolsRequest(Context context,
									 ProtocolsBaseRequest<T> request,
									 ProtocolsBaseCallback<T> callback,
									 LifecycleTransformer<T> lifecycleTransformer) {
		getProtocolsRequestObservable(context, request, lifecycleTransformer).subscribe(
			new ProtocolsSubscriber<>(context, request, callback));
	}

	/**
	 * 网络请求
	 *
	 * @param context  context
	 * @param request  request
	 * @param callback callback
	 * @param <T>      类型
	 */
	public <T> void protocolsRequest(Context context, ProtocolsBaseRequest<T> request, ProtocolsBaseCallback<T> callback) {
		getProtocolsRequestObservable(context, request).subscribe(new ProtocolsSubscriber<>(context, request, callback));
	}

	/**
	 * 合并两个请求的结果
	 *
	 * @param context    context
	 * @param requestOne request one
	 * @param requestTwo request two
	 * @param func       合并我们要得到的结果类型
	 * @param callback   callback
	 * @param <P>        request one 类型
	 * @param <N>        request two 类型
	 * @param <R>        最终callback返回类型
	 */
	public <P, N, R> void protocolsRequestZipWith(Context context,
												  ProtocolsBaseRequest<P> requestOne,
												  ProtocolsBaseRequest<N> requestTwo,
												  final ProtocolsZipFunc<P, N, R> func,
												  ProtocolsBaseCallback<R> callback) {

		Func1<Throwable, Observable<R>> mErrorResume = new Func1<Throwable, Observable<R>>() {
			@Override
			public Observable<R> call(Throwable throwable) {
				return Observable.error(transformerException(throwable));
			}
		};

		Observable<P> observableOne = getProtocolsRequestObservable(context, requestOne);
		Observable<N> observableTwo = getProtocolsRequestObservable(context, requestTwo);
		Observable<R> observable = observableOne.zipWith(observableTwo, new Func2<P, N, R>() {
			@Override
			public R call(P p, N n) {
				return func.call(p, n);
			}
		})
												.retryWhen(new RetryNetworkException())
												.onErrorResumeNext(mErrorResume)
												.subscribeOn(Schedulers.io())
												.unsubscribeOn(Schedulers.io())
												.observeOn(AndroidSchedulers.mainThread());

		observable.subscribe(new ProtocolsSubscriber<>(context, null, callback));
	}

	/**
	 * 顺序执行两个请求，并且前后有依赖
	 *
	 * @param context    context
	 * @param requestOne request one
	 * @param requestTwo request two
	 * @param func       上下来两个请求依赖处理
	 * @param callback   callback
	 * @param <P>        request one 类型
	 * @param <N>        request two 类型
	 */
	public <P, N> void protocolsRequestFlatMap(final Context context,
											   final ProtocolsBaseRequest<P> requestOne,
											   final ProtocolsBaseRequest<N> requestTwo,
											   final ProtocolsFlatMapFunc<P, N, ProtocolsBaseRequest<N>> func,
											   ProtocolsBaseCallback<N> callback) {

		final Observable<P> observableOne = getProtocolsRequestObservable(context, requestOne);

		Func1<Throwable, Observable<N>> mErrorResume = new Func1<Throwable, Observable<N>>() {
			@Override
			public Observable<N> call(Throwable throwable) {
				return Observable.error(transformerException(throwable));
			}
		};

		Observable<N> observable = observableOne.flatMap(new Func1<P, Observable<N>>() {
			@Override
			public Observable<N> call(P t) {
				return getProtocolsRequestObservable(context, func.call(t, requestTwo));
			}
		})
												.retryWhen(new RetryNetworkException())
												.onErrorResumeNext(mErrorResume)
												.subscribeOn(Schedulers.io())
												.unsubscribeOn(Schedulers.io())
												.observeOn(AndroidSchedulers.mainThread());

		observable.subscribe(new ProtocolsSubscriber<>(context, null, callback));
	}
}
