package com.tuacy.networkframe.library.cookie;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;


public class ProtocolsCookies implements CookieJar {

	private final Map<String, List<Cookie>> mCookiesMap = new HashMap<>();

	@Override
	public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
		String host = url.host();
		List<Cookie> cookiesList = mCookiesMap.get(host);
		if (cookiesList != null) {
			mCookiesMap.remove(host);
		}
		mCookiesMap.put(host, cookies);
	}

	@Override
	public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
		List<Cookie> cookiesList = mCookiesMap.get(url.host());
		return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
	}
}
