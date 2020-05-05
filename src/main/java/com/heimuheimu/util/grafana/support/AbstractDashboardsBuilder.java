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

package com.heimuheimu.util.grafana.support;

import com.heimuheimu.util.grafana.DashboardsBuilder;
import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.DashboardClient;
import com.heimuheimu.util.grafana.folder.Folder;
import com.heimuheimu.util.grafana.folder.FolderClient;
import com.heimuheimu.util.grafana.organization.Organization;
import com.heimuheimu.util.grafana.organization.OrganizationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * dashboards 生成器抽象类，提供生成器需要的公用方法。
 *
 * @author heimuheimu
 */
public abstract class AbstractDashboardsBuilder implements DashboardsBuilder {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * 组织信息 API 客户端
     */
    protected final OrganizationClient organizationClient;

    /**
     * 文件夹信息 API 客户端
     */
    protected final FolderClient folderClient;

    /**
     * Dashboard 信息 API 客户端
     */
    protected final DashboardClient dashboardClient;

    /**
     * 构造一个 AbstractDashboardsBuilder 实例。
     *
     * @param organizationClient 组织信息 API 客户端，不允许为 {@code null}
     * @param folderClient 文件夹信息 API 客户端，不允许为 {@code null}
     * @param dashboardClient Dashboard 信息 API 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 organizationClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 folderClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 dashboardClient 为 {@code null}，将会抛出此异常
     */
    protected AbstractDashboardsBuilder(OrganizationClient organizationClient, FolderClient folderClient,
                                        DashboardClient dashboardClient) throws NullPointerException {
        if (organizationClient == null) {
            String errorMessage = "Fails to create " + getClass().getSimpleName() + ": `organizationClient could not be null`";
            LOG.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        this.organizationClient = organizationClient;

        if (folderClient == null) {
            String errorMessage = "Fails to create " + getClass().getSimpleName() + ": `folderClient could not be null`";
            LOG.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        this.folderClient = folderClient;

        if (dashboardClient == null) {
            String errorMessage = "Fails to create " + getClass().getSimpleName() + ": `dashboardClient could not be null`";
            LOG.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        this.dashboardClient = dashboardClient;
    }

    @Override
    public void build(String organizationName, String interval, OutputStream outputStream) throws RuntimeException {
        if (organizationName == null || organizationName.trim().isEmpty()) {
            String errorMessage = "Fails to build dashboards: `organizationName could not be null or empty`.";
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        PrintWriter writer = null;
        if (outputStream != null) {
            writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
        }
        // create organization if absent
        Organization organization = organizationClient.createIfAbsent(organizationName);
        println(writer, "Fetch organization success: " + organization);
        organizationClient.switchOrganization(organization.getId());
        println(writer, "Switch organization success.");
        // create folder
        Folder folder = folderClient.create(getFolderName());
        println(writer, "Create folder success: " + folder);
        // create dashboards
        for (Dashboard dashboard : getDashboardList(organizationName, interval)) {
            dashboardClient.create(dashboard, folder.getId());
            println(writer, "Create dashboard success: `" + dashboard.getTitle() + "`.");
        }
        println(writer, "Create dashboards end. Builder: `" + getClass().getSimpleName() + "`.");
    }

    /**
     * 获得放置 dashboards 的文件夹名称。
     *
     * @return 放置 dashboards 的文件夹名称
     */
    protected abstract String getFolderName();

    /**
     * 获得需要创建的 dashboard 列表，不允许返回 {@code null} 或空。
     *
     * @param organizationName 组织名称
     * @param interval 图表目标数据获取周期，例如：30s、1m ...
     * @return 需要创建的 dashboard 列表，不允许为 {@code null} 或空
     */
    protected abstract List<Dashboard> getDashboardList(String organizationName, String interval);

    /**
     * 打印行日志，如果 writer 为 {@code null}，则不做任何操作。
     *
     * @param writer 日志输出
     * @param text 日志内容
     */
    protected void println(PrintWriter writer, String text) {
        if (writer != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            writer.println("[" + dateFormat.format(new Date()) + "] : " + text);
        }
    }
}
