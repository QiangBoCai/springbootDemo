package us.codecraft.webmagic.downloader;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * HttpClientRequestContext包含两个属性：
 * HttpUriRequest接:可作为CloseableHttpClient 的execute 方法 参数 得到CloseableResponse
 * HttpClientConetxt接口:实现了HttpContext接口，可作为CloseableHttpClient 的execute 方法 参数 得到CloseableResponse
 */
public class HttpClientRequestContext {

	
    private HttpUriRequest httpUriRequest;

    private HttpClientContext httpClientContext;

    public HttpUriRequest getHttpUriRequest() {
        return httpUriRequest;
    }

    public void setHttpUriRequest(HttpUriRequest httpUriRequest) {
        this.httpUriRequest = httpUriRequest;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public void setHttpClientContext(HttpClientContext httpClientContext) {
        this.httpClientContext = httpClientContext;
    }

}
