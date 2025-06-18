package com.gunr.bookreviewcolumn.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SocialUnlinkService {

    @Value("${naver_client_id}")
    private String naverClientId;

    @Value("${naver_client_secret}")
    private String naverClientSecret;

    public void unlink(String provider, String accessToken) {
        try {
            switch (provider) {
                case "kakao":
                    unlinkKakao(accessToken);
                    break;
                case "naver":
                    unlinkNaver(accessToken);
                    break;
                case "google":
                    unlinkGoogle(accessToken);
                    break;
                default:
                    System.out.println("지원하지 않는 provider: " + provider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unlinkKakao(String accessToken) throws Exception {
        URL url = new URL("https://kapi.kakao.com/v1/user/unlink");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.flush();
        os.close();

        System.out.println("Kakao 연동 해제 응답 코드: " + conn.getResponseCode());
        conn.disconnect();
    }

    private void unlinkNaver(String accessToken) throws Exception {
        URL url = new URL("https://nid.naver.com/oauth2.0/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String params = "grant_type=delete"
                + "&client_id=" + naverClientId
                + "&client_secret=" + naverClientSecret
                + "&access_token=" + accessToken
                + "&service_provider=naver";

        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        System.out.println("Naver 연동 해제 응답 코드: " + conn.getResponseCode());
        conn.disconnect();
    }

    private void unlinkGoogle(String accessToken) throws Exception {
        URL url = new URL("https://oauth2.googleapis.com/revoke?token=" + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.flush();
        os.close();

        System.out.println("Google 연동 해제 응답 코드: " + conn.getResponseCode());
        conn.disconnect();
    }
}

