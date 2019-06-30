package us.codecraft.webmagic;

import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.Experimental;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *Request 是对请求 Url 目标的封装
 * 也包含以下其他信息
 *  Method 请求方法 
 *  httpRequestBody ,请求消息内容
 *  extras 额外消息
 *  headers 消息头
 *  cookies 
 *  priority 优先级
 *
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;
    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";
    //网站
    private String url;
    //访问方法
    private String method;

    //请求消息体
    private HttpRequestBody requestBody;

    //存储附加信息  eg:CYCLE_TRIED_TIMES
    /**
     * 
     * Store additional information in extras.
     */
    private Map<String, Object> extras;
    
    // 如果没有设置Site的cookies，使用当前url的cookies
    /**
     * cookies for current url, if not set use Site's cookies
     */
    private Map<String, String> cookies = new HashMap<String, String>();

    // 请求消息头
    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Priority of the request.<br>
     * The bigger will be processed earlier. <br>
     * @see us.codecraft.webmagic.scheduler.PriorityScheduler
     */
    //消息处理的优先级
    private long priority;

    /**
     * When it is set to TRUE, the downloader will not try to parse response body to text.
     *
     */
    //是否将响应的消息体解析格式化的rawText内容，即带charset的String，默认false，表示需要
    
    private boolean binaryContent = false;
    //编码
    private String charset;

    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public long getPriority() {
        return priority;
    }

    /**
     * Set the priority of request for sorting.<br>
     * Need a scheduler supporting priority.<br>
     * @see us.codecraft.webmagic.scheduler.PriorityScheduler
     *
     * @param priority priority
     * @return this
     */
    @Experimental
    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    public Object getExtra(String key) {
        if (extras == null) {
            return null;
        }
        return extras.get(key);
    }

    public Request putExtra(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<String, Object>();
        }
        extras.put(key, value);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public Request setExtras(Map<String, Object> extras) {
        this.extras = extras;
        return this;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 默认请求方法 get
     * The http method of the request. Get for default.
     */
    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (url != null ? !url.equals(request.url) : request.url != null) return false;
        return method != null ? method.equals(request.method) : request.method == null;
    }

    public Request addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public Request addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(HttpRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public boolean isBinaryContent() {
        return binaryContent;
    }

    public Request setBinaryContent(boolean binaryContent) {
        this.binaryContent = binaryContent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Request setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", extras=" + extras +
                ", priority=" + priority +
                ", headers=" + headers +
                ", cookies="+ cookies+
                '}';
    }

}
