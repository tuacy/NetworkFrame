package com.tuacy.networkframe.library.base;

/**
 * 网络访问类型
 */

public enum ProtocolsNetworkAllowType {

	/**
	 * 所有网络都不允许访问
	 */
	ALLOW_NETWORK_NONE,
	/**
	 * wifi,mobile网络都可以访问
	 */
	ALLOW_NETWORK_ALL,
	/**
	 * wifi网络可以访问
	 */
	ALLOW_NETWORK_WIFI,
	/**
	 * mobile网络可以访问
	 */
	ALLOW_NETWORK_MOBILE

}
