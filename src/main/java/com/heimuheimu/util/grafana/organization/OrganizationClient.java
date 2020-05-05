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

package com.heimuheimu.util.grafana.organization;

import com.fasterxml.jackson.databind.JsonNode;
import com.heimuheimu.util.grafana.http.GrafanaHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Grafana 中的组织信息 API 客户端，更多信息请参考文档：
 * <a href="https://grafana.com/docs/grafana/latest/http_api/org/">Organization API</a>
 *
 * <p><strong>说明：</strong>OrganizationClient 类是线程安全的，可在多个线程中使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class OrganizationClient {

    private final static Logger LOG = LoggerFactory.getLogger(OrganizationClient.class);

    /**
     * Grafana http 客户端
     */
    private final GrafanaHttpClient grafanaHttpClient;

    /**
     * 构造一个 OrganizationClient 实例。
     *
     * @param grafanaHttpClient Grafana http 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 grafanaHttpClient 为 {@code null}，将会抛出此异常
     */
    public OrganizationClient(GrafanaHttpClient grafanaHttpClient) throws NullPointerException {
        if (grafanaHttpClient == null) {
            throw new NullPointerException("Fails to construct OrganizationClient: `grafanaHttpClient could not be null`.");
        }
        this.grafanaHttpClient = grafanaHttpClient;
    }

    /**
     * 根据组织名称获得组织信息，如果不存在，则返回 {@code null}。
     *
     * @param name 组织名称，不允许为 {@code null} 或空
     * @return 组织信息，可能为 {@code null}
     * @throws IllegalArgumentException 如果 name 为 {@code null} 或空，将会抛出此异常
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public Organization getByName(String name) throws RuntimeException {
        if (name == null || name.isEmpty()) {
            String errorMessage = "Fails to get organization: `name could not be null or empty`.";
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        JsonNode response = null;
        try {
            String path = "/api/orgs/name/" + name;
            response = grafanaHttpClient.get(path);
            if (response.has("id") && response.has("name")) {
                Organization organization = new Organization();
                organization.setId(response.get("id").intValue());
                organization.setName(response.get("name").textValue());
                return organization;
            } else {
                return null;
            }
        } catch (Exception e) {
            String errorMessage = "Fails to get organization. name: `" + name + "`. response: `" + response + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * 根据组织名称创建组织信息并返回，该方法不会返回 {@code null}。
     *
     * @param name 组织名称
     * @return 组织信息，不会返回 {@code null}
     * @throws IllegalArgumentException 如果 name 为 {@code null} 或空，将会抛出此异常
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public Organization create(String name) throws RuntimeException {
        if (name == null || name.isEmpty()) {
            String errorMessage = "Fails to create organization: `name could not be null or empty`.";
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        Organization organization = null;
        JsonNode response = null;
        try {
            String path = "/api/orgs";
            Map<String, String> body = new HashMap<>();
            body.put("name", name);
            response = grafanaHttpClient.post(path, body);
            if (response.has("orgId")) {
                organization = new Organization();
                organization.setId(response.get("orgId").intValue());
                organization.setName(name);
            }
        } catch (Exception e) {
            String errorMessage = "Fails to create organization. name: `" + name + "`. response: `" + response + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        if (organization == null) {
            String errorMessage = "Fails to create organization. name: `" + name + "`. response: `" + response + "`.";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return organization;
    }

    /**
     * 切换到指定的组织。
     *
     * @param id 组织 ID
     * @throws RuntimeException 如果执行过程中发生错误或切换失败，将会抛出此异常
     */
    public void switchOrganization(int id) throws RuntimeException {
        JsonNode response;
        try {
            String path = "/api/user/using/" + id;
            response = grafanaHttpClient.post(path, null);
        } catch (Exception e) {
            String errorMessage = "Fails to switch organization. OrgId: `" + id + "`.";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        if (!response.has("message") || !response.get("message").textValue().startsWith("Active")) {
            String errorMessage = "Fails to switch organization. OrgId: `" + id + "`. response: `" + response + "`.";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * 如果组织名称不存在，将会创建该组织信息并返回，如果已存在，将会返回已存在的组织信息，该方法不会返回 {@code null}。
     *
     * @param name 组织名称，不允许为 {@code null} 或空
     * @return 组织信息，不会返回 {@code null}
     * @throws RuntimeException 如果执行过程中发生错误，将会抛出此异常
     */
    public Organization createIfAbsent(String name) throws RuntimeException {
        Organization organization = getByName(name);
        if (organization == null) {
            organization = create(name);
        }
        return organization;
    }
}
