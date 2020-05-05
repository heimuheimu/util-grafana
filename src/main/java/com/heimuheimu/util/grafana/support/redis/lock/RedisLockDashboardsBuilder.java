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

package com.heimuheimu.util.grafana.support.redis.lock;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.DashboardClient;
import com.heimuheimu.util.grafana.folder.FolderClient;
import com.heimuheimu.util.grafana.organization.OrganizationClient;
import com.heimuheimu.util.grafana.support.AbstractDashboardsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 为 naiveredis 框架的 Redis 分布式锁生成对应的 dashboards，
 * 框架地址：<a href="http://github.com/heimuheimu/naiveredis/">naiveredis</a>
 */
public class RedisLockDashboardsBuilder extends AbstractDashboardsBuilder {

    /**
     * 构造一个 RedisLockDashboardsBuilder 实例。
     *
     * @param organizationClient 组织信息 API 客户端，不允许为 {@code null}
     * @param folderClient 文件夹信息 API 客户端，不允许为 {@code null}
     * @param dashboardClient Dashboard 信息 API 客户端，不允许为 {@code null}
     * @throws NullPointerException 如果 organizationClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 folderClient 为 {@code null}，将会抛出此异常
     * @throws NullPointerException 如果 dashboardClient 为 {@code null}，将会抛出此异常
     */
    public RedisLockDashboardsBuilder(OrganizationClient organizationClient, FolderClient folderClient, DashboardClient dashboardClient) throws NullPointerException {
        super(organizationClient, folderClient, dashboardClient);
    }

    @Override
    protected String getFolderName() {
        return "naiveredis-lock";
    }

    @Override
    protected List<Dashboard> getDashboardList(String organizationName, String interval) {
        List<Dashboard> dashboardList = new ArrayList<>();
        dashboardList.add(DistributedLockDashboardFactory.create(organizationName, interval));
        dashboardList.add(DistributedLockOverviewDashboardFactory.create(organizationName, interval));
        dashboardList.add(ErrorDashboardFactory.create(organizationName, interval));
        dashboardList.add(ExecutionDashboardFactory.create(organizationName, interval));
        dashboardList.add(ExecutionDetailDashboardFactory.create(organizationName, interval));
        dashboardList.add(ExecutionOverviewDashboardFactory.create(organizationName, interval));
        return dashboardList;
    }

    @Override
    public boolean isSupported(String type) {
        if (type != null) {
            type = type.toLowerCase();
            return type.contains("redis") && type.contains("lock");
        }
        return false;
    }
}