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

package com.heimuheimu.util.grafana.support.cache.memcached;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * Memcached 客户端执行详情信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ExecutionDetailDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ExecutionDetail");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Memcached 集群名称",
                        "naivecache_memcached_exec_count{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource))
                .addVariable(new Query("instance", "主机地址",
                        "naivecache_memcached_exec_count{job=\"" + job + "\"}", "/.*instance=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_count",
                "相邻两次采集周期内 Memcached 操作执行次数",
                new Graph.Target("naivecache_memcached_exec_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_peak_tps_count",
                "相邻两次采集周期内每秒最大 Memcached 操作执行次数",
                new Graph.Target("naivecache_memcached_exec_peak_tps_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_avg_exec_time_millisecond",
                "相邻两次采集周期内单次 Memcached 操作平均执行时间，单位：毫秒",
                new Graph.Target("naivecache_memcached_avg_exec_time_millisecond{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_max_exec_time_millisecond",
                "相邻两次采集周期内单次 Memcached 操作最大执行时间，单位：毫秒",
                new Graph.Target("naivecache_memcached_max_exec_time_millisecond{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_error_count:Timeout",
                "相邻两次采集周期内 Memcached 操作出现超时的错误次数",
                new Graph.Target("naivecache_memcached_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"Timeout\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_error_count:KeyNotFound",
                "相邻两次采集周期内 Memcached 操作出现超时的错误次数",
                new Graph.Target("naivecache_memcached_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"KeyNotFound\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_error_count:MemcachedError",
                "相邻两次采集周期内 Memcached 操作出现异常的错误次数",
                new Graph.Target("naivecache_memcached_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"MemcachedError\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_exec_error_count:SlowExecution",
                "相邻两次采集周期内 Memcached 操作出现执行过慢的错误次数",
                new Graph.Target("naivecache_memcached_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"SlowExecution\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
