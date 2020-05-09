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

package com.heimuheimu.util.grafana.support.rpc.client;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * RPC 客户端执行错误信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ErrorDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Error");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "RPC 服务名称",
                "instance:naiverpc_client_exec_error_count:sum{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_client_exec_error_count:sum:TooBusy",
                "相邻两次采集周期内调用 RPC 方法出现 RPC 服务端繁忙错误次数",
                new Graph.Target("instance:naiverpc_client_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"TooBusy\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_client_exec_error_count:sum:Timeout",
                "相邻两次采集周期内调用 RPC 方法出现执行超时错误次数",
                new Graph.Target("instance:naiverpc_client_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"Timeout\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_client_exec_error_count:sum:InvocationError",
                "相邻两次采集周期内调用 RPC 方法抛出异常的次数",
                new Graph.Target("instance:naiverpc_client_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"InvocationError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_client_exec_error_count:sum:SlowExecution",
                "相邻两次采集周期内调用 RPC 方法出现执行过慢的次数",
                new Graph.Target("instance:naiverpc_client_exec_error_count:sum{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"SlowExecution\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiverpc_client_threadPool_reject_count",
                "相邻两次采集周期内 RPC 客户端使用的线程池拒绝执行的任务总数",
                new Graph.Target("naiverpc_client_threadPool_reject_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));

        return dashboard;
    }
}
