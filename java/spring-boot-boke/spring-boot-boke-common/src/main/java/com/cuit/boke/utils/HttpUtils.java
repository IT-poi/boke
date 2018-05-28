package com.cuit.boke.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    static OkHttpClient client = null;

    static {
        client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    /**
     * post 请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postJson(String url, String json, String name, String value) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader(name, value).url(url).post(body).build();
        Response response = client.newCall(request).execute();
        logger.info(String.format("url[%s],http_code[%s]", url, response.code()));
        return response.body().string();
    }

    /**
     * post form 表单 application/x-www-form-urlencoded
     *
     * @param url
     * @param argus
     * @return
     * @throws Exception
     */
    public static String postMap(String url, Map<String, String> argus) throws Exception {
        List<String> l = Lists.newLinkedList();
        for (Iterator<String> it = argus.keySet().iterator(); it
                .hasNext(); ) {
            String key = it.next();
            l.add(key + "=" + encode(argus.get(key).toString()));
        }
        RequestBody body = RequestBody.create(FORM, Joiner.on("&").join(l));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        l.clear();
        l = null;
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static String encode(String value) throws Exception {
        if (value != null) {
            return URLEncoder.encode(value, "utf8");
        } else {
            return "";
        }

    }

    /**
     * GET请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

}