package com.xzh.http;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * http请求
 * @author 向振华
 * @date 2018/11/17 13:16
 */
public class HttpUitls {

    /**
     * NameValuePair格式
     * @param url 请求地址
     * @param data 数据
     */
    public static void postHttpClient(String url, NameValuePair[] data){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        postMethod.setRequestBody(data);
        try {
            int statusCode = httpClient.executeMethod(postMethod);

//            log.info("statusCode: " + statusCode + ", body: " + postMethod.getResponseBodyAsString());
        } catch (IOException ignored) {
        }
    }

    /**
     * json格式
     * @param url 请求地址
     * @param json 数据
     */
    public static void postJsonHttpClient(String url, String json){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
//            log.info("response: "+result);
        } catch (IOException ignored) {
        }
    }

}
