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
 * 数据库执行错误信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ErrorDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Error");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("database", "数据库", "mysql_jdbc_exec_error_count{job=\"" + job + "\"}",
                        "/.*database=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:Timeout",
                "相邻两次采集周期内 SQL 语句执行出现执行超时的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"Timeout\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:SlowExecution",
                "相邻两次采集周期内 SQL 语句执行出现执行过慢的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"SlowExecution\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:DuplicateEntryForKey",
                "相邻两次采集周期内 SQL 语句执行出现主键或唯一索引冲突的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"DuplicateEntryForKey\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:MysqlError",
                "相邻两次采集周期内 SQL 语句执行出现 MYSQL 服务端执行异常的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"MysqlError\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:IllegalState",
                "相邻两次采集周期内 SQL 语句执行出现管道或命令已关闭的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"IllegalState\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:InvalidParameter",
                "相邻两次采集周期内 SQL 语句执行出现参数值设置不正确的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"InvalidParameter\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:ResultSetError",
                "相邻两次采集周期内查询结果集 ResultSet 操作异常的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"ResultSetError\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_exec_error_count:UnexpectedError",
                "相邻两次采集周期内 SQL 语句执行出现预期外异常的错误次数",
                new Graph.Target("mysql_jdbc_exec_error_count{errorType=\"UnexpectedError\",database=\"[[database]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "mysql_jdbc_sql_feature_not_supported",
                "相邻两次采集周期内出现的 SQLFeatureNotSupportedException 异常次数，该指标不区分数据库实例",
                new Graph.Target("mysql_jdbc_sql_feature_not_supported{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
