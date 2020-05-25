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
 * 字节消息聚合信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ByteMessageOverviewDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ByteMessageOverview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_count:sum",
                "相邻两次采集周期内需要发送的消息总数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_total_megabytes:sum",
                "相邻两次采集周期内需要发送的字节总数，单位：MB，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_total_megabytes:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_max_bytes:max",
                "相邻两次采集周期内需要发送的单条消息最大字节数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_max_bytes:max{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_error_count:sum",
                "相邻两次采集周期内发送失败的消息总数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_error_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_sent_count:sum",
                "相邻两次采集周期内发送成功的消息总数，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_sent_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_sent_avg_delay_milliseconds:avg",
                "相邻两次采集周期内发送成功的消息平均延迟时间，单位：毫秒，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_sent_avg_delay_milliseconds:avg{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:raven_byte_message_sent_max_delay_milliseconds:max",
                "相邻两次采集周期内发送成功的消息最大延迟时间，单位：毫秒，根据项目名称进行聚合计算",
                new Graph.Target("job:raven_byte_message_sent_max_delay_milliseconds:max{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
