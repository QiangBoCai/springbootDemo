package us.codecraft.webmagic.downloader;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.utils.HttpConstant;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.Map;

/**
 * 
 * 负责根据Request，Site，Proxy
 * 生成HttpClientRequestContext  实例
 */
public class HttpUriRequestConverter {
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	/***
	 * 
	 * 根据Request Site ，Proxy生成HttpClientContext和HttpURIRequest
	 * 再实例化HttpClientRequestContext
	 * 
	 * @param request
	 * @param site
	 * @param proxy
	 * @return
	 */
    public HttpClientRequestContext convert(Request request, Site site, Proxy proxy) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(request, site, proxy));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(request, site, proxy));
        logger.debug("httpClientRequestContext URI"+httpClientRequestContext.getHttpUriRequest().getURI());
        
        return httpClientRequestContext;
    }

    /**
     * 根据Request，Site，Proxy生成 HttpClientContext
     * @param request
     * @param site
     * @param proxy
     * @return
     */
    private HttpClientContext convertHttpClientContext(Request request, Site site, Proxy proxy) {
        HttpClientContext httpContext = new HttpClientContext();
        if (proxy != null && proxy.getUsername() != null) {
            AuthState authState = new AuthState();
            authState.update(new BasicScheme(ChallengeState.PROXY), new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));
            httpContext.setAttribute(HttpClientContext.PROXY_AUTH_STATE, authState);
        }
        if (request.getCookies() != null && !request.getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : request.getCookies().entrySet()) {
                BasicClientCookie cookie1 = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(UrlUtils.removePort(UrlUtils.getDomain(request.getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        return httpContext;
    }

    
    /***
     * 根据Request，Site，Proxy生成 HttpURIRequest
     * 配置请求信息：请求方法，Url，超时限制，Cookile,Ip代理，请求Headers
     * 
     * @param request
     * @param site
     * @param proxy
     * @return
     */
    private HttpUriRequest convertHttpUriRequest(Request request, Site site, Proxy proxy) {
   
    	//  RequestBuilder 实例化
    	RequestBuilder requestBuilder = 
    						//设置请求方法
    						selectRequestMethod(request)
    						//根据Request的getUrl设置请求Url，先修复Url中的非法字符
    						.setUri(UrlUtils.fixIllegalCharacterInUrl(request.getUrl()));
    	
    	
    	
    	//根据Site的 getHeanders 获取到Headers
    	//设置RequestBuilder的 Headers
        if (site.getHeaders() != null) {
            for (Map.Entry<String, String> headerEntry : site.getHeaders().entrySet()) {
            	
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
                logger.debug("Site headerKey:"+headerEntry.getKey()+";headerValue:"+headerEntry.getValue());

            }
        }
       //获取RequestConfigBuilder 实例
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (site != null) {
            requestConfigBuilder.setConnectionRequestTimeout(site.getTimeOut())
                    .setSocketTimeout(site.getTimeOut())
                    .setConnectTimeout(site.getTimeOut())
                    .setCookieSpec(site.getCookieSpec());
                  //  .setCookieSpec(CookieSpecs.STANDARD);
			//此处默认写死，有一些网站不是采用的Standard，会识别不了Cookie,eg:CookieSpecs.BROWSER_COMPATIBILITY)
        }
        //RequestConfigBuilder 根据Proxy设置Ip代理
        if (proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        
        //RequestBuilder 配置RequestConfigBuilder
        requestBuilder.setConfig(requestConfigBuilder.build());
        
        //RequestBuilder build 实例化HttpUriRequest
        
        HttpUriRequest httpUriRequest = requestBuilder.build();
        //根据Request的Header获取到Headers，并配置Headers参数
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
                logger.debug("Request headerKey:"+header.getKey()+";headerValue:"+header.getValue());
            }
        }
        return httpUriRequest;
    }

    
    /**
     * 根据Request，getMethod选择请求方法
     * 
     * @param request
     * @return
     */
    private RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(),request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    /**
     * RequestBuilder ，根据Request的getRequestBody().getBody 添加表单参数
     * @param requestBuilder
     * @param request
     * @return
     */
    private RequestBuilder addFormParams(RequestBuilder requestBuilder, Request request) {
        if (request.getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(request.getRequestBody().getBody());
            entity.setContentType(request.getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }

}
