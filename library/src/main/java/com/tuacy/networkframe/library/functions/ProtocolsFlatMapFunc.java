package com.tuacy.networkframe.library.functions;


import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;

/**
 * 两个请求，下一个请求要使用到前一个请求的参数的时候
 *
 * @param <T> 上一个请求返回的数据
 * @param <R> 下一个请求返回的数据
 * @param <A> 下一个请求
 */
public interface ProtocolsFlatMapFunc<T, R, A extends ProtocolsBaseRequest<R>> {

	A call(T t, A a);
}
