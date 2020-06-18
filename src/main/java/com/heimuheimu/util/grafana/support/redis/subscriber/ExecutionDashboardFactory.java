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

package com.heimuheimu.util.grafana.support.redis.subscriber;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * Redis 消息订阅客户端执行信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ExecutionDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Execution");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Redis 集群名称",
                        "naiveredis_subscriber_exec_count{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_count",
                "相邻两次采集周期内消费消息总数",
                new Graph.Target("naiveredis_subscriber_exec_count{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_peak_tps_count",
                "相邻两次采集周期内每秒最大消费消息数量",
                new Graph.Target("naiveredis_subscriber_exec_peak_tps_count{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_avg_exec_time_millisecond",
                "相邻两次采集周期内消费单条消息平均执行时间",
                new Graph.Target("naiveredis_subscriber_avg_exec_time_millisecond{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_max_exec_time_millisecond",
                "相邻两次采集周期内消费单条消息最大执行时间",
                new Graph.Target("naiveredis_subscriber_max_exec_time_millisecond{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_error_count:DecodeError",
                "相邻两次采集周期内发生的消息解码失败次数",
                new Graph.Target("naiveredis_subscriber_exec_error_count{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"DecodeError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_error_count:ConsumeError",
                "相邻两次采集周期内发生的消息消费失败次数",
                new Graph.Target("naiveredis_subscriber_exec_error_count{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"ConsumeError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_error_count:UnexpectedError",
                "相邻两次采集周期内发生的消息消费出现预期外异常的次数",
                new Graph.Target("naiveredis_subscriber_exec_error_count{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"UnexpectedError\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_subscriber_exec_error_count:SlowConsumption",
                "相邻两次采集周期内发生的消息消费过慢的次数",
                new Graph.Target("naiveredis_subscriber_exec_error_count{name=~\"[[name]]\",job=\"[[job]]\",errorType=\"SlowConsumption\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
