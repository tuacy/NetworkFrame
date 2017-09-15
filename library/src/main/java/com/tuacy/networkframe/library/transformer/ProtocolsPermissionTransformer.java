package com.tuacy.networkframe.library.transformer;


import android.content.Context;
import android.support.annotation.NonNull;

import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;
import com.tuacy.networkframe.library.base.ProtocolsNetworkAllowType;
import com.tuacy.networkframe.library.exception.ProtocolsError;
import com.tuacy.networkframe.library.exception.ProtocolsException;
import com.tuacy.networkframe.library.utils.NetworkUtils;

import rx.Observable;

/**
 * 用于网络权限判断，在request里面可以设置网络的访问权限(wifi访问，mobile访问)
 *
 * @param <T> 类型
 */
public class ProtocolsPermissionTransformer<T> implements Observable.Transformer<T, T> {

	private Context                 mContext;
	private ProtocolsBaseRequest<T> mRequest;

	public ProtocolsPermissionTransformer(Context context, ProtocolsBaseRequest<T> request) {
		mContext = context;
		mRequest = request;
	}

	@Override
	public Observable<T> call(Observable<T> observable) {
		if (!allowNetwork(mContext, mRequest.getNetworkAllowType())) {
			return Observable.error(new ProtocolsException(ProtocolsError.ERROR_PERMISSION));
		}
		return observable;
	}

	private boolean allowNetwork(@NonNull Context context, ProtocolsNetworkAllowType allowType) {
		boolean allow = false;
		switch (allowType) {
			case ALLOW_NETWORK_NONE:
				allow = false;
				break;
			case ALLOW_NETWORK_ALL:
				allow = NetworkUtils.isNetworkConnect(context);
				break;
			case ALLOW_NETWORK_WIFI:
				allow = NetworkUtils.isNetworkConnect(context) && NetworkUtils.isWifiConnected(context);
				break;
			case ALLOW_NETWORK_MOBILE:
				allow = NetworkUtils.isNetworkConnect(context) && NetworkUtils.isMobileConnected(context);
				break;
		}
		return allow;
	}
}
