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

package com.heimuheimu.util.grafana.support.redis.client;

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
class OverviewDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Overview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Redis 集群名称",
                        "job:naiveredis_exec_count:sum{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_exec_count:sum",
                "相邻两次采集周期内 Redis 操作执行次数，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_exec_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_exec_peak_tps_count:sum",
                "相邻两次采集周期内每秒最大 Redis 操作执行次数，根据项目名称和 Redis 集群名称进行聚合计算（该值为估算值，实际值一般小于该估算值）",
                new Graph.Target("job:naiveredis_exec_peak_tps_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_max_exec_time_millisecond:max",
                "相邻两次采集周期内单次 Redis 操作最大执行时间，单位：毫秒，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_max_exec_time_millisecond:max{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_avg_exec_time_millisecond:avg",
                "相邻两次采集周期内单次 Redis 操作平均执行时间，单位：毫秒，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_avg_exec_time_millisecond:avg{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_exec_error_count:sum",
                "相邻两次采集周期内 Redis 操作失败次数（不包含 Key 不在的错误次数），根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_threadPool_reject_count:sum",
                "相邻两次采集周期内 Redis 客户端使用的线程池拒绝执行的任务总数，根据项目名称进行聚合计算，不区分具体的 Redis 集群名称",
                new Graph.Target("job:naiveredis_threadPool_reject_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_socket_read_megabytes:sum",
                "相邻两次采集周期内 Socket 读取的字节总数，单位：MB，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_socket_read_megabytes:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveredis_socket_write_megabytes:sum",
                "相邻两次采集周期内 Socket 写入的字节总数，单位：MB，根据项目名称和 Redis 集群名称进行聚合计算",
                new Graph.Target("job:naiveredis_socket_write_megabytes:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{name}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
