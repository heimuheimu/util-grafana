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
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * Redis 客户端聚合信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ExecutionOverviewDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ExecutionOverview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Redis 集群名称",
                        "job:naiveredis_lock_client_exec_count:sum{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_lock_client_exec_count:sum",
                "相邻两次采集周期内 Redis 分布式锁客户端获取锁的次数，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_lock_client_exec_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_lock_client_exec_peak_tps_count:sum",
                "相邻两次采集周期内 Redis 分布式锁客户端每秒最大获取锁的次数，根据项目名称和 Redis 集群名称进行聚合计算（该值为估算值，实际值一般小于该估算值）",
                new Graph.Target("job:naiveredis_lock_client_exec_peak_tps_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_lock_client_max_exec_time_millisecond:max",
                "相邻两次采集周期内单次 Redis 分布式锁客户端单次获取锁的最大执行时间，单位：毫秒，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_lock_client_max_exec_time_millisecond:max{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_lock_client_avg_exec_time_millisecond:avg",
                "相邻两次采集周期内 Redis 分布式锁客户端单次获取锁的平均执行时间，单位：毫秒，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_lock_client_avg_exec_time_millisecond:avg{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_lock_client_exec_error_count:sum",
                "相邻两次采集周期内 Redis 分布式锁客户端获取错误次数（包含锁已被占用错误），根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_lock_client_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
