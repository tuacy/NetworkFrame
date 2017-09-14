package com.tuacy.networkframe.library;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;
import com.tuacy.networkframe.library.base.ProtocolsNetworkAllowType;
import com.tuacy.networkframe.library.callback.ProtocolsBaseCallback;
import com.tuacy.networkframe.library.exception.ProtocolsError;
import com.tuacy.networkframe.library.exception.ProtocolsException;
import com.tuacy.networkframe.library.utils.NetworkUtils;

import rx.Subscriber;

public final class ProtocolsSubscriber<T> extends Subscriber<T> {

	private Context                  mContext;
	private ProtocolsBaseRequest<T>  mRequest;
	private ProtocolsBaseCallback<T> mCallback;

	public ProtocolsSubscriber(Context context, ProtocolsBaseRequest<T> request) {
		this(context, request, null);
	}

	public ProtocolsSubscriber(Context context, ProtocolsBaseRequest<T> request, ProtocolsBaseCallback<T> callback) {
		mContext = context;
		mRequest = request;
		mCallback = callback;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mCallback != null) {
			mCallback.onProtocolStart(mRequest.getTag());
			if (!allowNetwork(mContext, mRequest.getNetworkAllowType())) {
				unsubscribe();
				mCallback.onProtocolError(mRequest.getTag(), new ProtocolsException(ProtocolsError.ERROR_NETWORK_PERMISSION));
			}
		}

	}

	@Override
	public void onCompleted() {
		//do noting
	}

	@Override
	public void onError(Throwable e) {
		if (mCallback != null) {
			if (e instanceof ProtocolsException) {
				mCallback.onProtocolError(mRequest.getTag(), (ProtocolsException) e);
			} else {
				mCallback.onProtocolError(mRequest.getTag(), new ProtocolsException(e, ProtocolsError.ERROR_UNKNOWN));
			}
		}
	}

	@Override
	public void onNext(T t) {
		if (mCallback != null) {
			mCallback.onProtocolSuccess(mRequest.getTag(), t);
		}
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
