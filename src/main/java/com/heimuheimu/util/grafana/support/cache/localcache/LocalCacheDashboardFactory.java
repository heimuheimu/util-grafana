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

package com.heimuheimu.util.grafana.support.cache.localcache;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;

/**
 * 本地缓存客户端压缩操作信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class LocalCacheDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("LocalCache");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_total_count",
                "采集时刻本地缓存中保存的 Key 的总个数",
                new Graph.Target("naivecache_local_cache_total_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_query_count",
                "相邻两次采集周期内本地缓存执行 get 操作的次数",
                new Graph.Target("naivecache_local_cache_query_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_query_hit_count",
                "相邻两次采集周期内本地缓存执行 get 操作的命中次数",
                new Graph.Target("naivecache_local_cache_query_hit_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_added_count",
                "相邻两次采集周期内本地缓存新增 Key 的个数",
                new Graph.Target("naivecache_local_cache_added_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_deleted_count",
                "相邻两次采集周期内本地缓存删除 Key 的个数",
                new Graph.Target("naivecache_local_cache_deleted_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_local_cache_error_count",
                "相邻两次采集周期内本地缓存操作出现异常的错误次数",
                new Graph.Target("naivecache_local_cache_error_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
