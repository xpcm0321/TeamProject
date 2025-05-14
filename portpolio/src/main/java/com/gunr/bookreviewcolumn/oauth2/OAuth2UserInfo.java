package com.gunr.bookreviewcolumn.oauth2;

public interface OAuth2UserInfo {
	String getProviderId();
    String getProvider();
    String getProviderEmail();
    String getProviderName();
}
