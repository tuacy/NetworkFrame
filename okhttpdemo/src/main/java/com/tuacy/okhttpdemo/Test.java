package com.tuacy.okhttpdemo;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Challenge;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.InternalCache;
import okio.BufferedSink;
import okio.ByteString;


public interface Test {

	/**
	 * 通过传入网络请求的封装，返回一个Call调用，通过Call来发起或者取消网络请求。
	 * @param request 网络请求的封装
	 * @return 返回一个Call调用
	 */
	public Call newCall(Request request);

	/**
	 * 获取该OkHttpClient对应的Builder(为了保证OkHttpClient在整个应用中共用一个连接池和缓存)
	 * @return OkHttpClient.Builder
	 */
	public OkHttpClient.Builder newBuilder();




	/********** Call *********/
	/**
	 * 获取request(网络请求参数的封装)
	 */
	Request request();

	/**
	 * 同步请求
	 * @throws IllegalStateException 当这个Call已经在请求的时候爬出IllegalStateException
	 */
	Response execute() throws IOException;

	/**
	 * 异步请求数据
	 * @throws IllegalStateException 当这个Call已经在请求的时候爬出IllegalStateException
	 */
	void enqueue(Callback responseCallback);

	/**
	 * 取消当前请求
	 */
	void cancel();

	/**
	 * 网络请求是否在进行(可以在请求之前判断下)
	 */
	boolean isExecuted();

	/**
	 * 网络请求是否取消
	 */
	boolean isCanceled();

	/******* response *******/
	/**
	 * 获取当前请求的request
	 */
	public Request request();

	/**
	 * 获取当前请求的协议例如{@link Protocol#HTTP_1_1} or {@link Protocol#HTTP_1_0}.
	 */
	public Protocol protocol();

	/**
	 * 获取当前请求的状态码
	 */
	public int code();

	/**
	 * 当前请求是否成功[状态码200..300)
	 */
	public boolean isSuccessful();

	/**
	 * 当前请求状态码对应的message
	 */
	public String message();

	/**
	 * 获取当前请求SSL/TLS握手协议验证时的信息
	 */
	public Handshake handshake();

	/**
	 * 获取当前请求返回的header
	 */
	public List<String> headers(String name);

	/**
	 * 获取当前请求返回的header
	 */
	public String header(String name);

	/**
	 * 获取当前请求返回的header(如果不存在返回默认值)
	 */
	public String header(String name, String defaultValue);

	/**
	 *  取出当前请求返回的body(设置字节的长度)
	 */
	public ResponseBody peekBody(long byteCount) throws IOException;

	/**
	 * 取出当前请求返回的body
	 */
	public ResponseBody body();

	/**
	 * 是否重定向了
	 */
	public boolean isRedirect();

	/**
	 * 网络返回的原声数据(如果未使用网络，则为null)
	 */
	public Response networkResponse();

	/**
	 * 从cache中读取的网络原生数据
	 */
	public Response cacheResponse();

	/**
	 * 网络重定向后的,存储的上一次网络请求返回的数据
	 */
	public Response priorResponse();

	/**
	 * 获得所有当前response所有支持的认证
	 */
	public List<Challenge> challenges();

	/**
	 * response里面header Cache-Control 里面的信息
	 */
	public CacheControl cacheControl();

	/**
	 * 发起请求的时间
	 */
	public long sentRequestAtMillis();

	/**
	 * 收到返回数据时的时间
	 */
	public long receivedResponseAtMillis();

	/********* RequestBody ********/
	/**
	 * 设置request body 的类型
	 */
	public abstract MediaType contentType();

	/**
	 * 设置request body 的长度
	 */
	public long contentLength() throws IOException;

	/**
	 * 把body的内容写到BufferedSink里面去
	 */
	public abstract void writeTo(BufferedSink sink) throws IOException;

	/**
	 * 返回一个RequestBody,body的能容是String
	 */
	public static RequestBody create(MediaType contentType, String content);

	/**
	 * 返回一个RequestBody,body的能容是ByteString
	 */
	public static RequestBody create(final MediaType contentType, final ByteString content);

	/**
	 * 返回一个RequestBody,body的能容是Byte[]
	 */
	public static RequestBody create(final MediaType contentType, final byte[] content);

	/**
	 * 返回一个RequestBody,body的能容是Byte[],而且规定了开始位置，和字符长度
	 */
	public static RequestBody create(final MediaType contentType, final byte[] content,
									 final int offset, final int byteCount);


	/**
	 * 返回一个RequestBody,body的能容是File
	 */
	public static RequestBody create(final MediaType contentType, final File file);
}
