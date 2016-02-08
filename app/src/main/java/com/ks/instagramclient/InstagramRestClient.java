package com.ks.instagramclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by skammila on 2/3/16.
 */
public class InstagramRestClient {

    public static final String INSTAGRAM_BASE_URL = "https://api.instagram.com";
//    public static final String POPULAR_MEDIA_URI = "/v1/media/popular?access_token=" + "2078451801.1fb234f.5efbad60844e4e37b22eeb3b0029528b";
    public static final String POPULAR_MEDIA_URI = "/v1/media/popular?client_id=" + "e05c462ebd86446ea48a5af73769b602";
    private static final String ACCESS_TOKEN = "2078451801.1fb234f.5efbad60844e4e37b22eeb3b0029528b";
    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        if (params == null) {
//            params = new RequestParams();
//        }
//        params.add("access_token", ACCESS_TOKEN);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (params == null) {
            params = new RequestParams();
        }
        params.add("access_token", ACCESS_TOKEN);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getPopularMedia(JsonHttpResponseHandler responseHandler) {
        InstagramRestClient.get(POPULAR_MEDIA_URI, null, responseHandler );
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return INSTAGRAM_BASE_URL + relativeUrl;
    }
}
