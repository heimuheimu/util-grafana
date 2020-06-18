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
 * Redis 客户端执行错误信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ErrorDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Error");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Redis 集群名称",
                "instance:naiveredis_exec_error_count:sum{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:IllegalArgument",
                "相邻两次采集周期内 Redis 操作出现参数不正确的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"IllegalArgument\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:IllegalState",
                "相邻两次采集周期内 Redis 操作出现管道或命令已关闭的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"IllegalState\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:Timeout",
                "相邻两次采集周期内 Redis 操作出现执行超时的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"Timeout\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:RedisError",
                "相邻两次采集周期内 Redis 操作出现 Redis 服务端执行异常的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"RedisError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:KeyNotFound",
                "相邻两次采集周期内 Redis 操作出现 Key 不存在的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"KeyNotFound\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:UnexpectedError",
                "相邻两次采集周期内 Redis 操作出现预期外异常的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"UnexpectedError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiveredis_exec_error_count:sum:SlowExecution",
                "相邻两次采集周期内 Redis 操作出现执行过慢的错误次数",
                new Graph.Target("instance:naiveredis_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"SlowExecution\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_threadPool_reject_count",
                "相邻两次采集周期内 Redis 客户端使用的线程池拒绝执行的任务总数",
                new Graph.Target("naiveredis_threadPool_reject_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_cluster_unavailable_client_count",
                "相邻两次采集周期内 Redis 集群客户端获取到不可用 Redis 客户端的次数",
                new Graph.Target("naiveredis_cluster_unavailable_client_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_cluster_multi_get_error_count",
                "相邻两次采集周期内 Redis 集群客户端调用 multiGet 方法出现错误的次数",
                new Graph.Target("naiveredis_cluster_multi_get_error_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
