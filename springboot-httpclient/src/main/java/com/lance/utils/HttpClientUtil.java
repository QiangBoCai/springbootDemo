package com.lance.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * HttpClient 常用刚发
 *
 * @author xiaotao
 * @version 1.0 on 2018-05-10
 */
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
    //连接目标超时connectionTimeout
    private static final int ConnectTimeout = 2 * 1000;
    //	等待响应超时（读取数据超时）SocketTimeout
    private static final int SocketTimeout = 2 * 1000;
    
    private static HttpClientUtil httpRequest = null;

    public static HttpClientUtil getInstance() {
        if (httpRequest == null) {
            synchronized (HttpClientUtil.class) {
                if (httpRequest == null)
                    httpRequest = new HttpClientUtil();
            }
        }
        return httpRequest;
    }

    public static void main(String[] args) {
        String aa = "{\"head\":{\"messagetype\":\"Request\",\"messageid\":\"5000000003\",\"tranid\":\"50000201610250913331\"},\"body\":{\"changingtime\":\"2016-12-21 14:09:43\",\"msisdnlist\":[{\"msisdn\":\"8618682176636\"},{\"msisdn\":\"86170342469\"},{\"msisdn\":\"86186890163\"}]}}";
        String url = "http://localhost1/change";

        long start = System.currentTimeMillis();
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        httpClientUtil.excuteHttpPost(aa, url);
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 发送post请求
     * 成功返回正常值，失败返回error,超时返回sockettimeout
     */
    public static String doPost(String postJsonStr, String url) {
        String result = "error";
        //创建HttpClient对象
        CloseableHttpClient closeHttpClient = HttpClients.custom().build();
        CloseableHttpResponse httpResponse = null;
        try {
            //发送Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(ConnectTimeout).setSocketTimeout(SocketTimeout).build();
            httpPost.setConfig(config);
            StringEntity ss = new StringEntity(postJsonStr, ContentType.create("application/json", "UTF-8"));
            httpPost.setEntity(ss);
            //执行Post请求 得到Response对象
            System.out.println("send post");
            httpResponse = closeHttpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
                logger.info("http post result=" + result);
            } else {
                logger.info("http post error,url=" + url);
            }
        } catch (SocketTimeoutException e) {
            logger.error("http post error: SocketTimeout ", e);
            return "socket timeout";
        } catch (IOException e) {
            logger.error("http post error ", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.error("http post error ", e);
                }
            }
            if (closeHttpClient != null) {
                try {
                    closeHttpClient.close();
                } catch (IOException e) {
                    logger.error("http post error ", e);
                }
            }
        }
        return result;
    }

    /**
     * 未收到请求时，重发一定次数请求。
     */
    public String excuteHttpPost(String postJsonStr, String url) {

        //重发次数
        int repeatNum = 1;
        String result = null;
        while (repeatNum >= 0) {
            result = doPost(postJsonStr, url);
            if ("error".equals(result) || "socket timeout".equals(result)) {
                repeatNum--;
            } else {
                break;
            }
        }
        return result;
    }
}
