package com.xzh.utils.http;

import lombok.extern.slf4j.Slf4j;
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

/**
 * http请求
 *
 * @author 向振华
 * @date 2018/11/17 13:16
 */
@Slf4j
public class HttpUtils {

    /**
     * NameValuePair格式
     *
     * @param url 请求地址
     * @param data 数据
     */
    public static String postHttpClient(String url, NameValuePair[] data){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        postMethod.setRequestBody(data);
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            String result = postMethod.getResponseBodyAsString();
            log.info("http-response: "+result);
            return result;
        } catch (Exception e) {
            log.error("postHttpClient-Exception：",e);
            throw new RuntimeException("http请求异常！");
        }
    }

    /**
     * json格式
     *
     * @param url 请求地址
     * @param json 数据
     */
    public static String postJsonHttpClient(String url, String json){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("http-response: "+result);
            return result;
        } catch (Exception e) {
            log.error("postJsonHttpClient-Exception：",e);
            throw new RuntimeException("http请求异常！");
        }
    }
}
