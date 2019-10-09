package com.xzh.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.time.LocalDateTime;

/**
 * 机器人webhook
 *
 * @author: 向振华
 * @date: 2019/09/19 15:42
 */
@Slf4j
public class RobotUtils {

    /**
     * 机器人webhook
     */
    private static final String WEBHOOK= "xxx";

    /**
     * 发送消息
     */
    public static void send(String message){
        message = LocalDateTime.now().withNano(0).toString().replace("T", " ") + "\n" + message;
        message = message.replace("{", "").replace("}", "").replace("\"", " ");
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        String text ="{ \"msgtype\": \"text\", \"text\": {\"content\": \""+message+"\"}}";
        StringEntity se = null;
        try {
            se = new StringEntity(text, "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result= EntityUtils.toString(response.getEntity(), "utf-8");
                log.info("机器人：{}", result);
            }
        } catch (Exception ignored) {
        }
    }
}