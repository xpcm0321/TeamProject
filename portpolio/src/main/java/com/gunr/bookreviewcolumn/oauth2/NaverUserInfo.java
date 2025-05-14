package com.gunr.bookreviewcolumn.oauth2;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getProviderId() {
        return (String) attributes.get("id");
    }

    public String getProvider() {
        return "naver";
    }

    public String getProviderEmail() {
        return (String) attributes.get("email");
    }

    public String getProviderName() {
        return (String) attributes.get("name");
    }
}

