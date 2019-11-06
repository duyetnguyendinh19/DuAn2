package com.vn.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vn.jpa.GmailGoogle;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;


public class GoogleUtils {

    private static String LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    private static String LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    private static String CLIENT_ID = "498115250422-8p16v252evqielnvh0jmidb402o92j7k.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "-vwO6ouapDqaIPmyVCtLJgJM";
    private static String REDIRECT_URI = "http://localhost:8284/duan2_war/home/login-google";
    private static String GRANT_TYPE = "authorization_code";

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", CLIENT_ID)
                        .add("client_secret", CLIENT_SECRET)
                        .add("redirect_uri", REDIRECT_URI).add("code", code)
                        .add("grant_type", GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GmailGoogle getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GmailGoogle googlePojo = new Gson().fromJson(response, GmailGoogle.class);
        System.out.println(googlePojo);
        return googlePojo;

    }
}
