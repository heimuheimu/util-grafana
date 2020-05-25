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

package com.heimuheimu.util.grafana.support.raven;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;

/**
 * IM 客户端管理器执行信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ManagerExecutionDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ManagerExecution");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_exec_count",
                "相邻两次采集周期内迭代所有可用的 IM 客户端执行次数",
                new Graph.Target("raven_manager_exec_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_exec_peak_tps_count",
                "相邻两次采集周期内每秒最大迭代所有可用的 IM 客户端执行次数",
                new Graph.Target("raven_manager_exec_peak_tps_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_avg_exec_time_millisecond",
                "相邻两次采集周期内单次迭代所有可用的 IM 客户端操作平均执行时间，单位：毫秒",
                new Graph.Target("raven_manager_avg_exec_time_millisecond{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_max_exec_time_millisecond",
                "相邻两次采集周期内单次迭代所有可用的 IM 客户端操作最大执行时间，单位：毫秒",
                new Graph.Target("raven_manager_max_exec_time_millisecond{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_exec_error_count:RegisterError",
                "相邻两次采集周期内出现 IM 客户端注册失败的错误次数",
                new Graph.Target("raven_manager_exec_error_count{job=\"[[job]]\",errorType=\"RegisterError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_exec_error_count:CommunicateError",
                "相邻两次采集周期内出现 IM 客户端通信失败的错误次数",
                new Graph.Target("raven_manager_exec_error_count{job=\"[[job]]\",errorType=\"CommunicateError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_manager_exec_error_count:SelectError",
                "相邻两次采集周期内出现 IM 客户端选择失败的错误次数",
                new Graph.Target("raven_manager_exec_error_count{job=\"[[job]]\",errorType=\"SelectError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
