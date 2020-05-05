/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 heimuheimu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.heimuheimu.util.grafana.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Grafana http 客户端，用于访问 Grafana 提供的 http api 接口，更多信息请参考文档：
 * <a href="https://grafana.com/docs/grafana/latest/http_api/auth/">Authentication API</a>
 *
 * <p><strong>说明：</strong>GrafanaHttpClient 类是线程安全的，可在多个线程中使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class GrafanaHttpClient {

    /**
     * Http 访问记录日志
     */
    private final static Logger HTTP_CLIENT_LOG = LoggerFactory.getLogger("HTTP_CLIENT_LOG");

    private final static Logger LOG = LoggerFactory.getLogger(GrafanaHttpClient.class);

    /**
     * JSON 解析器
     */
    private final static ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();

    /**
     * Http 客户端
     */
    private final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    /**
     * Basic auth 使用的用户名
     */
    private final String username;

    /**
     * Basic auth 使用的密码
     */
    private final String password;

    /**
     * Grafana 服务主机地址，例如："localhost"
     */
    private final String host;

    /**
     * Grafana 服务端口地址，例如：3000
     */
    private final int port;

    /**
     * 是否使用 https 协议
     */
    private final boolean isHttps;

    /**
     * 构造一个 GrafanaHttpClient 实例，使用 http 协议。
     *
     * @param username Basic auth 使用的用户名
     * @param password Basic auth 使用的密码
     * @param host Grafana 服务主机地址
     * @param port Grafana 服务端口
     */
    public GrafanaHttpClient(String username, String password, String host, int port) {
        this(username, password, host, port, false);
    }

    /**
     * 构造一个 GrafanaHttpClient 实例。
     *
     * @param username Basic auth 使用的用户名
     * @param password Basic auth 使用的密码
     * @param host Grafana 服务主机地址
     * @param port Grafana 服务端口
     * @param isHttps 是否使用 https 协议
     */
    public GrafanaHttpClient(String username, String password, String host, int port, boolean isHttps) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.isHttps = isHttps;
    }

    /**
     * 通过 Get 请求访问 Grafana http api 接口，并返回 JSON 响应对象。
     *
     * @param path 访问路径
     * @return JSON 响应对象
     * @throws RuntimeException 如果访问过程中出现错误，将会抛出此异常
     */
    public JsonNode get(String path) throws RuntimeException {
        long startTime = System.currentTimeMillis();
        try {
            Request request = getRequestBuilder(path).get().build();
            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
                //noinspection ConstantConditions
                String responseText = response.body().string();
                HTTP_CLIENT_LOG.info("[Get] cost: `{}ms`. url: `{}`. response: `{}`.", System.currentTimeMillis() - startTime,
                        request.url(), responseText);
                return JSON_OBJECT_MAPPER.readTree(responseText);
            }
        } catch (Exception e) {
            String errorMessage = "[Get] Invoke grafana http api failed. path: `" + path + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * 通过 Post 请求访问 Grafana http api 接口，并返回 JSON 响应对象。
     *
     * @param path 访问路径
     * @param body post 内容，允许为 {@code null}
     * @return JSON 响应对象
     * @throws RuntimeException 如果访问过程中出现错误，将会抛出此异常
     */
    public JsonNode post(String path, Object body) throws RuntimeException {
        long startTime = System.currentTimeMillis();
        try {
            String postJson = body != null ? JSON_OBJECT_MAPPER.writeValueAsString(body) : null;
            RequestBody requestBody = postJson != null ? RequestBody.create(postJson, MediaType.get("application/json; charset=utf-8")) :
                    RequestBody.create(new byte[0]);
            Request request = getRequestBuilder(path).post(requestBody).build();
            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
                //noinspection ConstantConditions
                String responseText = response.body().string();
                HTTP_CLIENT_LOG.info("[Post] cost: `{}ms`. url: `{}`. body: `{}`. response: `{}`.", System.currentTimeMillis() - startTime,
                        request.url(), postJson, responseText);
                return JSON_OBJECT_MAPPER.readTree(responseText);
            }
        } catch (Exception e) {
            String errorMessage = "[Post] Invoke grafana http api failed. path: `" + path + "`. body: `" + body + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private Request.Builder getRequestBuilder(String path) throws URISyntaxException {
        URI uri = new URI(isHttps ? "https" : "http", null, host, port, path, null, null);
        return new Request.Builder().addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(username, password))
                .url(uri.toASCIIString());
    }
}
