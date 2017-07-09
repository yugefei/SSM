package cn.edu.njue.blackStone.backend.utils;

import cn.edu.njue.blackStone.backend.utils.exception.RestClientException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * 请求服务的client类，根据需要扩展
 * Created by 刘 on 2015/5/10 20:11
 */
public class RestClient {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String DEFAULT_MEDIA = MediaType.APPLICATION_JSON;

    /**
     * @param url  有返回的get
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T get(String url, Class<T> type) throws RestClientException {
        ClientRequest request = getClientRequest(url);
        ClientResponse<?> response = null;
        try {
            response = request.get();
        } catch (Exception e) {
            throw new RestClientException(String.format("[GET WRONG:%s]", url), e);
        }
        checkStatus(url, response);
        return response.getEntity(type);
    }

    /**
     * 没有返回的get
     * @param url
     * @throws RestClientException
     */
    public static void get(String url) throws RestClientException {
        ClientRequest request = getClientRequest(url);
        ClientResponse<?> response = null;
        try {
            response = request.get();
        } catch (Exception e) {
            throw new RestClientException(String.format("[GET WRONG:%s]", url), e);
        }
        checkStatus(url, response);
    }

    /**
     * 有返回,带url参数和header的get
     * @param url
     * @throws RestClientException
     */
    public static <T> T get(String url, Map<String,String> param,Map<String,String> headers, Class<T> type) throws RestClientException {
        ClientRequest request = getClientRequest(url);
        if(headers != null){
            for(String key : headers.keySet()){
                request.header(key,headers.get(key));
            }
        }

        if(param != null){
            for(String key : param.keySet()){
                request.pathParameter(key,param.get(key));
            }
        }

        ClientResponse<?> response = null;
        try {
            response = request.get();
        } catch (Exception e) {
            throw new RestClientException(String.format("[GET WRONG:%s]", url), e);
        }

        checkStatus(url, response);

        return response.getEntity(type);
    }

    /**
     * 不需要返回的post
     *
     * @param url
     * @param param
     * @return
     */


    public static void post(String url, Object param) throws RestClientException {
        post(url, param, DEFAULT_MEDIA);
    }



    public static void delete(String url) throws Exception {
        ClientRequest request = getClientRequest(url);
        ClientResponse response = request.delete();
        checkStatus(url,response);
    }

    /**
     * 可选传输截体的post
     *
     * @param url url
     * @param param param
     * @param media media
     */
    public static void post(String url, Object param, String media) throws RestClientException {
        ClientRequest request = getClientRequest(url);
        if(null != param){
            request.body(media, param);
        }
        ClientResponse<?> response = null;
        try {
            response = request.post();
        } catch (Exception e) {
            throw new RestClientException(String.format("[POST WRONG:%s]", url), e);
        }
        checkStatus(url, response);
    }


    /**
     * 有返回的post
     *
     * @param url
     * @param data
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T post(String url, Object data, Class<T> type) throws RestClientException {
        return post(url, data, type, DEFAULT_MEDIA);
    }

    /**
     * 没有参数，但是有返回
     * @param url 请求地址
     * @param type
     * @param <T> 期待返回数型
     * @return 反射确定数据
     * @throws RestClientException 异常
     */
    public static <T> T post(String url, Class<T> type) throws RestClientException {
        return post(url, null, type, null);
    }

    public static <T> T post(String url, Object data, Class<T> type, String media) throws RestClientException {
        ClientRequest request = getClientRequest(url);
        if(null != data){
            request.body(media, data);
        }
        ClientResponse<T> response = null;
        try {
            response = request.post(type);
        } catch (Exception e) {
            throw new RestClientException(String.format("[POST WRONG:%s]", url), e);
        }
        checkStatus(url, response);
        return response.getEntity(type);
    }

    protected static ClientRequest getClientRequest(String url) {
        ClientRequest client = new ClientRequest(url);
        client.header("charset", DEFAULT_CHARSET);
        return client;
    }

    public static void checkStatus(String url, ClientResponse<?> response) throws RestClientException {
        Response.Status  status= response.getResponseStatus();
        if (!Response.Status.OK.equals(status) && !Response.Status.NO_CONTENT.equals(status) && !Response.Status.CREATED.equals(status)) {
            String message ="请求错误"+url;
            if(null != status){
                message = String.format("url=%s %s %s ", url, status.toString(), response.getEntity(String.class));
            }
            throw new RestClientException(message);
        }
    }
}
