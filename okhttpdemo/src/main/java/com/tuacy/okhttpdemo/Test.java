package com.tuacy.okhttpdemo;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.internal.InternalCache;


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
	 *
	 */
	public List<Interceptor> interceptors();

	/**
	 *
	 */
	public OkHttpClient.Builder addInterceptor(Interceptor interceptor);

	/**
	 *
	 */
	public List<Interceptor> networkInterceptors();

	/**
	 *
	 */
	public OkHttpClient.Builder addNetworkInterceptor(Interceptor interceptor);
}
