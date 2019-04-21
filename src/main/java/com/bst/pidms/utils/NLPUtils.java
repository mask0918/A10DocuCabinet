package com.bst.pidms.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author: BST
 * @Date: 2019/4/18 19:40
 */
public class NLPUtils {
    public static String getKeywords(String url, String val, String title) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httppost = new HttpPost(url + "?content=" + URLEncoder.encode(val, "UTF-8") + "&title=" + URLEncoder.encode(title, "UTF-8"));
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                EntityUtils.consume(resEntity);
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
