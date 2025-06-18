package com.gunr.bookreviewcolumn.oauth2;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getProviderId() {
    	return (String) attributes.get("sub"); // 구글은 sub이 고유 id
    }

    public String getProvider() {
        return "google";
    }

    public String getProviderEmail() {
        return (String) attributes.get("email");
    }

    public String getProviderName() {
        return (String) attributes.get("name");
    }
}
