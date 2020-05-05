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
 * 数据库连接池信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class DatasourceDashboardFactory {

    public static Dashboard create(String job, String interval) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Datasource");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("database", "数据库", "mysql_jdbc_datasource_acquired_connection_count{job=\"" + job + "\"}",
                        "/.*database=\"([^\"]*).*/"));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_datasource_acquired_connection_count",
                "采集时刻连接池正在使用的连接数量",
                new Graph.Target("mysql_jdbc_datasource_acquired_connection_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_datasource_max_acquired_connection_count",
                "相邻两次采集周期内连接池使用的最大连接数量",
                new Graph.Target("mysql_jdbc_datasource_max_acquired_connection_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_datasource_connection_leaked_count",
                "相邻两次采集周期内连接池发生连接泄漏的次数",
                new Graph.Target("mysql_jdbc_datasource_connection_leaked_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_datasource_get_connection_failed_count",
                "相邻两次采集周期内连接池获取不到连接的次数",
                new Graph.Target("mysql_jdbc_datasource_get_connection_failed_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval"));
        return dashboard;
    }
}
