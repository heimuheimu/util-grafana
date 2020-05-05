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

package com.heimuheimu.util.grafana.dashboard;

import com.fasterxml.jackson.databind.JsonNode;
import com.heimuheimu.util.grafana.http.GrafanaHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Grafana 中的 dashboard 信息 API 客户端，更多信息请参考文档：
 * <a href="https://grafana.com/docs/grafana/latest/http_api/dashboard/">Dashboard API</a>
 *
 * <p><strong>说明：</strong>DashboardClient 类是线程安全的，可在多个线程中使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class DashboardClient {

    private final static Logger LOG = LoggerFactory.getLogger(DashboardClient.class);

    /**
     * Grafana http 客户端
     */
    private final GrafanaHttpClient grafanaHttpClient;

    /**
     * 构造一个 DashboardClient 实例。
     *
     * @param grafanaHttpClient Grafana http 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 grafanaHttpClient 为 {@code null}，将会抛出此异常
     */
    public DashboardClient(GrafanaHttpClient grafanaHttpClient) throws NullPointerException {
        if (grafanaHttpClient == null) {
            throw new NullPointerException("Fails to construct DashboardClient: `grafanaHttpClient could not be null`.");
        }
        this.grafanaHttpClient = grafanaHttpClient;
    }

    /**
     * 创建 dashboard。
     *
     * @param dashboard 需要创建的 dashboard 信息，不允许为 {@code null}
     * @param folderId 文件夹 ID，如果小于 0，则不指定
     * @throws NullPointerException 如果 dashboard 为 {@code null}，将会抛出此异常
     * @throws RuntimeException 如果创建过程中出现错误，将会抛出此异常
     */
    public void create(Dashboard dashboard, int folderId) throws RuntimeException {
        if (dashboard == null) {
            String errorMessage = "Fails to create dashboard: `dashboard could not be null`.";
            LOG.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        JsonNode response;
        try {
            String path = "/api/dashboards/db";
            Map<String, Object> body = new HashMap<>();
            body.put("dashboard", dashboard);
            if (folderId >= 0) {
                body.put("folderId", folderId);
            }
            body.put("overwrite", false);
            response = grafanaHttpClient.post(path, body);
        } catch (Exception e) {
            String errorMessage = "Fails to create dashboard. dashboard: `" + dashboard + "`. folderId: `"
                    + folderId + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        if (!response.has("id")) {
            String errorMessage = "Fails to create dashboard. dashboard: `" + dashboard + "`. folderId: `" + folderId
                    + "`. response: `" + response + "`.";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }
}
