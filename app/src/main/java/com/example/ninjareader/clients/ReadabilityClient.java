package com.example.ninjareader.clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by mvince on 2/25/15.
 */
public class ReadabilityClient {
    private static final String BASE_URL = "https://readability.com/api/content/v1/parser?token=82d707b4ad3f26438ecb6935e48610a49ed981ef";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getArticleInfo(String articleUrl, AsyncHttpResponseHandler responseHandler) {
        String formattedUrl = BASE_URL + "&url=" + articleUrl;
        client.get(formattedUrl, responseHandler);
    }
}
