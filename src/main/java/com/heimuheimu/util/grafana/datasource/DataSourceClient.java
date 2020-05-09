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

package com.heimuheimu.util.grafana.datasource;

import com.fasterxml.jackson.databind.JsonNode;
import com.heimuheimu.util.grafana.http.GrafanaHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Grafana 中的数据源信息 API 客户端，更多信息请参考文档：
 * <a href="https://grafana.com/docs/grafana/latest/http_api/data_source/">Data source API</a>
 *
 * <p><strong>说明：</strong>DataSourceClient 类是线程安全的，可在多个线程中使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class DataSourceClient {

    private final static Logger LOG = LoggerFactory.getLogger(DataSourceClient.class);

    /**
     * Grafana http 客户端
     */
    private final GrafanaHttpClient grafanaHttpClient;

    /**
     * 构造一个 DataSourceClient 实例。
     *
     * @param grafanaHttpClient Grafana http 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 grafanaHttpClient 为 {@code null}，将会抛出此异常
     */
    public DataSourceClient(GrafanaHttpClient grafanaHttpClient) throws NullPointerException {
        if (grafanaHttpClient == null) {
            throw new NullPointerException("Fails to construct DataSourceClient: `grafanaHttpClient could not be null`.");
        }
        this.grafanaHttpClient = grafanaHttpClient;
    }

    /**
     * 根据数据源名称获得数据源信息，如果不存在，则返回 {@code null}。
     *
     * @param name 数据源名称，不允许为 {@code null} 或空
     * @return 数据源信息，可能为 {@code null}
     * @throws IllegalArgumentException 如果 name 为 {@code null} 或空，将会抛出此异常
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public DataSource getByName(String name) throws RuntimeException {
        if (name == null || name.isEmpty()) {
            String errorMessage = "Fails to get DataSource: `name could not be null or empty`.";
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        JsonNode response = null;
        try {
            String path = "/api/datasources/name/" + name;
            response = grafanaHttpClient.get(path);
            if (response.has("id") && response.has("name")) {
                DataSource dataSource = new DataSource();
                dataSource.setId(response.get("id").intValue());
                dataSource.setName(response.get("name").textValue());
                dataSource.setType(response.get("type").textValue());
                dataSource.setUrl(response.get("url").textValue());
                dataSource.setAccess(response.get("access").textValue());
                return dataSource;
            } else {
                return null;
            }
        } catch (Exception e) {
            String errorMessage = "Fails to get DataSource. name: `" + name + "`. response: `" + response + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * 创建指定的数据源并返回设置 ID 后的数据源信息，该方法不会返回 {@code null}。
     *
     * @param dataSource 数据源信息，数据源 ID 会被忽略
     * @return 数据源信息，不会返回 {@code null}
     * @throws NullPointerException 如果 dataSource 为 {@code null}，将会抛出此异常
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public DataSource create(DataSource dataSource) throws RuntimeException {
        if (dataSource == null) {
            String errorMessage = "Fails to create DataSource: `dataSource could not be null`.";
            LOG.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        DataSource result = null;
        JsonNode response = null;
        try {
            String path = "/api/datasources";
            Map<String, String> body = new HashMap<>();
            body.put("name", dataSource.getName());
            body.put("type", dataSource.getType());
            body.put("url", dataSource.getUrl());
            body.put("access", dataSource.getAccess());
            response = grafanaHttpClient.post(path, body);
            if (response.has("datasource")) {
                JsonNode datasourceResponse = response.get("datasource");
                if (datasourceResponse.has("id") && datasourceResponse.has("name")) {
                    result = new DataSource();
                    result.setId(datasourceResponse.get("id").intValue());
                    result.setName(datasourceResponse.get("name").textValue());
                    result.setType(datasourceResponse.get("type").textValue());
                    result.setUrl(datasourceResponse.get("url").textValue());
                    result.setAccess(datasourceResponse.get("access").textValue());
                }
            }
        } catch (Exception e) {
            String errorMessage = "Fails to create DataSource. dataSource: `" + dataSource + "`. response: `" + response + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        if (result == null) {
            String errorMessage = "Fails to create DataSource. dataSource: `" + dataSource + "`. response: `" + response + "`.";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return result;
    }
}
