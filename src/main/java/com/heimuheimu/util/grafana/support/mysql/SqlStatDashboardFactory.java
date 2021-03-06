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
 * 数据库 SQL 语句统计信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class SqlStatDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("SqlStat");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("database", "数据库", "mysql_jdbc_select_count{job=\"" + job + "\"}",
                        "/.*database=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_select_count",
                "相邻两次采集周期内 SELECT 语句执行总次数",
                new Graph.Target("mysql_jdbc_select_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_select_rows_count",
                "相邻两次采集周期内所有 SELECT 语句返回的记录总数",
                new Graph.Target("mysql_jdbc_select_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_max_select_rows_count",
                "相邻两次采集周期内单条 SELECT 语句返回的最大记录数",
                new Graph.Target("mysql_jdbc_max_select_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_insert_count",
                "相邻两次采集周期内所有 INSERT 语句执行总次数",
                new Graph.Target("mysql_jdbc_insert_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_insert_rows_count",
                "相邻两次采集周期内所有 INSERT 语句插入的记录总数",
                new Graph.Target("mysql_jdbc_insert_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_max_insert_rows_count",
                "相邻两次采集周期内单条 INSERT 语句插入的最大记录数",
                new Graph.Target("mysql_jdbc_max_insert_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_update_count",
                "相邻两次采集周期内所有 UPDATE 语句执行总次数",
                new Graph.Target("mysql_jdbc_update_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_update_rows_count",
                "相邻两次采集周期内所有 UPDATE 语句更新的记录总数",
                new Graph.Target("mysql_jdbc_update_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_max_update_rows_count",
                "相邻两次采集周期内单条 UPDATE 语句更新的最大记录数",
                new Graph.Target("mysql_jdbc_max_update_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_delete_count",
                "相邻两次采集周期内所有 DELETE 语句执行总次数",
                new Graph.Target("mysql_jdbc_delete_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_delete_rows_count",
                "相邻两次采集周期内所有 DELETE 语句删除的记录总数",
                new Graph.Target("mysql_jdbc_delete_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_max_delete_rows_count",
                "相邻两次采集周期内单条 DELETE 语句删除的最大记录数",
                new Graph.Target("mysql_jdbc_max_delete_rows_count{database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
