package com.tuacy.networkframe.library.functions;


/**
 * 和并两个请求
 *
 * @param <P> 上一个请求返回的数据
 * @param <N> 下一个请求返回的数据
 * @param <R> 最终想要的结果
 */
public interface ProtocolsZipFunc<P, N, R> {

	R call(P p, N n);
}
