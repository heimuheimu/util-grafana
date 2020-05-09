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
 * Redis 客户端执行详情信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ExecutionDetailDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ExecutionDetail");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Redis 集群名称",
                        "naiveredis_exec_count{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource))
                .addVariable(new Query("instance", "主机地址",
                        "naiveredis_exec_count{job=\"" + job + "\"}", "/.*instance=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_count",
                "相邻两次采集周期内 Redis 操作执行次数",
                new Graph.Target("naiveredis_exec_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_peak_tps_count",
                "相邻两次采集周期内每秒最大 Redis 操作执行次数",
                new Graph.Target("naiveredis_exec_peak_tps_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_avg_exec_time_millisecond",
                "相邻两次采集周期内单次 Redis 操作平均执行时间，单位：毫秒",
                new Graph.Target("naiveredis_avg_exec_time_millisecond{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_max_exec_time_millisecond",
                "相邻两次采集周期内单次 Redis 操作最大执行时间，单位：毫秒",
                new Graph.Target("naiveredis_max_exec_time_millisecond{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:IllegalArgument",
                "相邻两次采集周期内 Redis 操作出现参数不正确的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"IllegalArgument\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:IllegalState",
                "相邻两次采集周期内 Redis 操作出现管道或命令已关闭的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"IllegalState\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:Timeout",
                "相邻两次采集周期内 Redis 操作出现执行超时的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"Timeout\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:RedisError",
                "相邻两次采集周期内 Redis 操作出现 Redis 服务端执行异常的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"RedisError\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:KeyNotFound",
                "相邻两次采集周期内 Redis 操作出现 Key 不存在的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"KeyNotFound\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:UnexpectedError",
                "相邻两次采集周期内 Redis 操作出现预期外异常的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"UnexpectedError\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_exec_error_count:SlowExecution",
                "相邻两次采集周期内 Redis 操作出现执行过慢的错误次数",
                new Graph.Target("naiveredis_exec_error_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\",errorType=\"SlowExecution\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
