package com.tuacy.networkframe.library;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.tuacy.networkframe.library.base.ProtocolBaseRequest;
import com.tuacy.networkframe.library.callback.ProtocolBaseCallback;
import com.tuacy.networkframe.library.exception.ProtocolError;
import com.tuacy.networkframe.library.exception.ProtocolException;

import java.util.List;

import rx.Subscriber;

public final class ProtocolSubscriber<T> extends Subscriber<T> {

	private Context                 mContext;
	private ProtocolBaseRequest<T>  mRequest;
	private ProtocolBaseCallback<T> mCallback;

	public ProtocolSubscriber(Context context, ProtocolBaseRequest<T> request) {
		this(context, request, null);
	}

	public ProtocolSubscriber(Context context, ProtocolBaseRequest<T> request, ProtocolBaseCallback<T> callback) {
		mContext = context;
		mRequest = request;
		mCallback = callback;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mCallback != null) {
			mCallback.onProtocolStart(mRequest.getTag());
			//判断网络是否连接
			if (isNetworkAvailable(mContext)) {
				unsubscribe();
				mCallback.onProtocolError(mRequest.getTag(), new ProtocolException(ProtocolError.ERROR_NETWORK_UNAVAILABLE));
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
			if (e instanceof ProtocolException) {
				mCallback.onProtocolError(mRequest.getTag(), (ProtocolException) e);
			} else {
				mCallback.onProtocolError(mRequest.getTag(), new ProtocolException(e, ProtocolError.ERROR_UNKNOWN));
			}
		}
	}

	@Override
	public void onNext(T t) {
		if (mCallback != null) {
			mCallback.onProtocolSuccess(mRequest.getTag(), t);
		}
	}

	/**
	 * 判断网络连接是否可用
	 *
	 * @param context context
	 * @return 网络连接是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断GPS是否打开
	 *
	 * @param context context
	 * @return GPS是否打开
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = lm.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	/**
	 * 判断WIFI是否打开
	 *
	 * @param context context
	 * @return WIFI是否打开
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) ||
				mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * 判断是否是3G网络
	 *
	 * @param context context
	 * @return 是否是3G网络
	 */
	public static boolean is3rd(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是wifi网络
	 *
	 * @param context context
	 * @return 是否是wifi网络
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
}
