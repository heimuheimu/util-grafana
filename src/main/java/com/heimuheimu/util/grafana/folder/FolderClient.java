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

package com.heimuheimu.util.grafana.folder;

import com.fasterxml.jackson.databind.JsonNode;
import com.heimuheimu.util.grafana.http.GrafanaHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Grafana 中的文件夹信息 API 客户端，更多信息请参考文档：
 * <a href="https://grafana.com/docs/grafana/latest/http_api/folder/">Folder API</a>
 *
 * <p><strong>说明：</strong>FolderClient 类是线程安全的，可在多个线程中使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class FolderClient {

    private final static Logger LOG = LoggerFactory.getLogger(FolderClient.class);

    /**
     * Grafana http 客户端
     */
    private final GrafanaHttpClient grafanaHttpClient;

    /**
     * 构造一个 FolderClient 实例。
     *
     * @param grafanaHttpClient Grafana http 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 grafanaHttpClient 为 {@code null}，将会抛出此异常
     */
    public FolderClient(GrafanaHttpClient grafanaHttpClient) throws NullPointerException {
        if (grafanaHttpClient == null) {
            throw new NullPointerException("Fails to construct FolderClient: `grafanaHttpClient could not be null`.");
        }
        this.grafanaHttpClient = grafanaHttpClient;
    }

    /**
     * 根据文件夹名称创建文件夹信息并返回，该方法不会返回 {@code null}。
     *
     * @param title 文件夹名称
     * @return 文件夹信息，不会为 {@code null}
     * @throws IllegalArgumentException 如果 title 为 {@code null} 或空，将会抛出此异常
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public Folder create(String title) throws RuntimeException {
        if (title == null || title.isEmpty()) {
            String errorMessage = "Fails to create folder: `title could not be null or empty`.";
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        Folder folder = null;
        JsonNode response = null;
        try {
            String path = "/api/folders";
            Map<String, String> body = new HashMap<>();
            body.put("title", title);
            response = grafanaHttpClient.post(path, body);
            if (response.has("id")) {
                folder = new Folder();
                folder.setId(response.get("id").intValue());
                folder.setTitle(title);
            }
        } catch (Exception e) {
            String errorMessage = "Fails to create folder. title: `" + title + "`. response: `" + response + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        if (folder == null) {
            String errorMessage = "Fails to create folder. title: `" + title + "`. response: `" + response + "`.";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return folder;
    }
}
