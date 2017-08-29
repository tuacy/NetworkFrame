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
	 * 设置connect超时时间
	 */
	public OkHttpClient.Builder connectTimeout(long timeout, TimeUnit unit);

	/**
	 * 数据read超时时间
	 */
	public OkHttpClient.Builder readTimeout(long timeout, TimeUnit unit);

	/**
	 * 数据write超时时间
	 */
	public OkHttpClient.Builder writeTimeout(long timeout, TimeUnit unit);

	/**
	 * 设置HTTP代理连接(通过别的地址去上网)
	 */
	public OkHttpClient.Builder proxy(Proxy proxy);

	/**
	 * 设置HTTP的自动代理选择器(有时候需要根据不同的网址旋转不同的代理)
	 */
	public OkHttpClient.Builder proxySelector(ProxySelector proxySelector);

	/**
	 * 设置cookie持久化的处理
	 */
	public OkHttpClient.Builder cookieJar(CookieJar cookieJar);

	/**
	 * 设置缓存
	 */
	public OkHttpClient.Builder cache(Cache cache);

	/**
	 * 设置DNS解析服务器
	 */
	public OkHttpClient.Builder dns(Dns dns);

	/**
	 * SocketFactory创建socket的工厂类，可以自己去实现(咱们一般很少会去改变这个)
	 */
	public OkHttpClient.Builder socketFactory(SocketFactory socketFactory);

	/**
	 * SSLSocketFactory创建加密socket的工厂类(咱们一般很少会去改变这个)https时候用到
	 */
	public OkHttpClient.Builder sslSocketFactory(SSLSocketFactory sslSocketFactory);

	/**
	 * X509TrustManager
	 */
	public OkHttpClient.Builder sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager);

	/**
	 * 设置服务器验证我们访问的IP地址方式
	 */
	public OkHttpClient.Builder hostnameVerifier(HostnameVerifier hostnameVerifier);

	/**
	 * 设置那些证书是可以信任的
	 */
	public OkHttpClient.Builder certificatePinner(CertificatePinner certificatePinner);

	/**
	 * http值401的时候，会调用设置类里面的方法(比如token过期之类的，然后咱们可以在这里面重新设置新的token)
	 */
	public OkHttpClient.Builder authenticator(Authenticator authenticator);

	/**
	 * 代理服务器需要确认身份
	 */
	public OkHttpClient.Builder proxyAuthenticator(Authenticator proxyAuthenticator);

	/**
	 * 自定义链接池线程数量啥的，
	 */
	public OkHttpClient.Builder connectionPool(ConnectionPool connectionPool);

	/**
	 * 是否允许https重定向
	 */
	public OkHttpClient.Builder followSslRedirects(boolean followProtocolRedirects);

	/**
	 * 是否允许http重定向
	 */
	public OkHttpClient.Builder followRedirects(boolean followRedirects);

	/**
	 * 是否自动重连
	 */
	public OkHttpClient.Builder retryOnConnectionFailure(boolean retryOnConnectionFailure);

	/**
	 * 异步任务的Call会放到Dispatcher调度器里面去处理，咱们可以自己去处理调度的逻辑
	 */
	public OkHttpClient.Builder dispatcher(Dispatcher dispatcher);

	/**
	 * OkHttp支持的协议类型
	 */
	public OkHttpClient.Builder protocols(List<Protocol> protocols);

	/**
	 * HTTPS具体的安全与连接的决定是由ConnectionSpec接口实现(可以用一组自定义TLS版本和密码套件建立自己的连接规格)
	 */
	public OkHttpClient.Builder connectionSpecs(List<ConnectionSpec> connectionSpecs);

	/**
	 * 获取应用拦截器
	 */
	public List<Interceptor> interceptors();

	/**
	 * 添加应用拦截器
	 */
	public OkHttpClient.Builder addInterceptor(Interceptor interceptor);

	/**
	 * 获取网络拦截器
	 */
	public List<Interceptor> networkInterceptors();

	/**
	 * 添加网络拦截器
	 */
	public OkHttpClient.Builder addNetworkInterceptor(Interceptor interceptor);



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

	/******** request ***********/
	/**
	 * 设置当前request的url
	 */
	public Request.Builder url(HttpUrl url);

	/**
	 * 设置当前request的url
	 */
	public Request.Builder url(String url);

	/**
	 * 设置当前request的url
	 */
	public Request.Builder url(URL url);

	/**
	 * 添加当前请求header(key-value的形式如果当前header的key存在则会替换)
	 */
	public Request.Builder header(String name, String value);

	/**
	 * 添加当前请求header(允许存在多个相同的key)
	 */
	public Request.Builder addHeader(String name, String value);

	/**
	 * 移除当前请求header
	 */
	public Request.Builder removeHeader(String name);

	/**
	 * 设置请求头(之前设置的无效)
	 */
	public Request.Builder headers(Headers headers);

	/**
	 * 设置cache相关的请求头(Cache-Control)
	 */
	public Request.Builder cacheControl(CacheControl cacheControl);

	/**
	 * GET请求
	 */
	public Request.Builder get();

	/**
	 * HEAD请求
	 */
	public Request.Builder head();

	/**
	 * POST请求+BODY
	 */
	public Request.Builder post(RequestBody body);

	/**
	 * DELETE请求+BODY
	 */
	public Request.Builder delete(RequestBody body);

	/**
	 * DELETE请求+无BODY
	 */
	public Request.Builder delete();

	/**
	 * PUT请求+BODY
	 */
	public Request.Builder put(RequestBody body);

	/**
	 * PATCH请求+BODY
	 */
	public Request.Builder patch(RequestBody body);

	/**
	 * 自己写请求方式+BODY
	 */
	public Request.Builder method(String method, RequestBody body);

	/**
	 * 设置当前请求的tag
	 */
	public Request.Builder tag(Object tag);

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
