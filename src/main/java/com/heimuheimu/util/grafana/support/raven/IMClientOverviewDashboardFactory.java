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
 * IM 客户端聚合信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class IMClientOverviewDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("IMClientOverview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_established_count:sum",
                "采集时刻保持连接的 IM 客户端数量，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_established_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_created_count:sum",
                "相邻两次采集周期内创建的 IM 客户端数量，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_created_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_closed_count:sum",
                "相邻两次采集周期内关闭的 IM 客户端数量，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_closed_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_established_error_count:sum",
                "相邻两次采集周期内 IM 客户端创建失败的次数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_established_error_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_closed_error_count:sum",
                "相邻两次采集周期内 IM 客户端关闭失败的次数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_closed_error_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_client_timeout_count:sum",
                "相邻两次采集周期内发生超时错误的 IM 客户端数量，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_client_timeout_count:sum{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
