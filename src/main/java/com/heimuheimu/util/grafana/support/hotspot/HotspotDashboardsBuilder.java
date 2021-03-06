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

package com.heimuheimu.util.grafana.support.hotspot;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.DashboardClient;
import com.heimuheimu.util.grafana.datasource.DataSource;
import com.heimuheimu.util.grafana.datasource.DataSourceClient;
import com.heimuheimu.util.grafana.folder.FolderClient;
import com.heimuheimu.util.grafana.organization.OrganizationClient;
import com.heimuheimu.util.grafana.support.AbstractDashboardsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM 监控信息图表生成器，对应的 Prometheus 监控指标采集器：
 * <a href="https://github.com/heimuheimu/naivemonitor/blob/master/src/main/java/com/heimuheimu/naivemonitor/prometheus/support/hotspot/HotspotCompositePrometheusCollector.java">
 *     HotspotCompositePrometheusCollector
 * </a>
 *
 * @author heimuheimu
 */
public class HotspotDashboardsBuilder extends AbstractDashboardsBuilder {

    /**
     * 构造一个 HotspotDashboardsBuilder 实例。
     *
     * @param organizationClient 组织信息 API 客户端，不允许为 {@code null}
     * @param dataSourceClient 数据源信息 API 客户端，不允许为 {@code null}
     * @param dataSource 数据源信息，数据源 ID 会被忽略，不允许为 {@code null}
     * @param folderClient 文件夹信息 API 客户端，不允许为 {@code null}
     * @param dashboardClient Dashboard 信息 API 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 organizationClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 dataSourceClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 dataSource 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 folderClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 dashboardClient 为 {@code null}，将会抛出此异常
     */
    public HotspotDashboardsBuilder(OrganizationClient organizationClient, DataSourceClient dataSourceClient,
                                    DataSource dataSource, FolderClient folderClient, DashboardClient dashboardClient) throws NullPointerException {
        super(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
    }

    @Override
    protected String getFolderName() {
        return "hotspot";
    }

    @Override
    protected List<Dashboard> getDashboardList(String organizationName, String interval) {
        List<Dashboard> dashboardList = new ArrayList<>();
        dashboardList.add(ClassloadingDashboardFactory.create(organizationName, interval, dataSource.getName()));
        dashboardList.add(GarbageCollectorDashboardFactory.create(organizationName, interval, dataSource.getName()));
        dashboardList.add(MemoryDashboardFactory.create(organizationName, interval, dataSource.getName()));
        dashboardList.add(MemoryPoolDashboardFactory.create(organizationName, interval, dataSource.getName()));
        dashboardList.add(ThreadDashboardFactory.create(organizationName, interval, dataSource.getName()));
        return dashboardList;
    }

    @Override
    public boolean isSupported(String type) {
        return type != null && (type.toLowerCase().contains("hotspot") || type.toLowerCase().contains("jvm"));
    }
}
