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

package com.heimuheimu.util.grafana.support.mysql;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * 数据库聚合信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class OverviewDashboardFactory {

    public static Dashboard create(String job, String interval) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Overview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("database", "数据库", "job:mysql_jdbc_exec_count:sum{job=\"" + job + "\"}",
                        "/.*database=\"([^\"]*).*/"));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_exec_count:sum",
                "相邻两次采集周期内 SQL 语句执行次数，根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_exec_count:sum{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_exec_error_count:sum",
                "相邻两次采集周期内 SQL 语句执行失败次数（包含慢查），根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_exec_error_count:sum{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_exec_peak_tps_count:sum",
                "相邻两次采集周期内每秒最大 SQL 语句执行次数，根据项目名称和数据库名称进行聚合计算（该值为估算值，实际值一般小于该估算值）",
                new Graph.Target("job:mysql_jdbc_exec_peak_tps_count:sum{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_max_exec_time_millisecond:max",
                "相邻两次采集周期内单条 SQL 语句最大执行时间，单位：毫秒，根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_max_exec_time_millisecond:max{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_avg_exec_time_millisecond:avg",
                "相邻两次采集周期内每条 SQL 语句平均执行时间，单位：毫秒，根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_avg_exec_time_millisecond:avg{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_socket_read_megabytes:sum",
                "相邻两次采集周期内 Socket 读取的字节总数，单位：MB，根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_socket_read_megabytes:sum{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:mysql_jdbc_socket_write_megabytes:sum",
                "相邻两次采集周期内 Socket 写入的字节总数，单位：MB，根据项目名称和数据库名称进行聚合计算",
                new Graph.Target("job:mysql_jdbc_socket_write_megabytes:sum{database=~\"[[database]]\",job=\"[[job]]\"}", "{{database}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval"));
        return dashboard;
    }
}
