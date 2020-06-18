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
 * 字节消息信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class ByteMessageDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("ByteMessage");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_count",
                "相邻两次采集周期内需要发送的消息总数",
                new Graph.Target("raven_byte_message_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_total_megabytes",
                "相邻两次采集周期内需要发送的字节总数，单位：MB",
                new Graph.Target("raven_byte_message_total_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_max_bytes",
                "相邻两次采集周期内需要发送的单条消息最大字节数",
                new Graph.Target("raven_byte_message_max_bytes{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_sent_count",
                "相邻两次采集周期内发送成功的消息总数",
                new Graph.Target("raven_byte_message_sent_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_sent_avg_delay_milliseconds",
                "相邻两次采集周期内发送成功的消息平均延迟时间，单位：毫秒",
                new Graph.Target("raven_byte_message_sent_avg_delay_milliseconds{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "raven_byte_message_sent_max_delay_milliseconds",
                "相邻两次采集周期内发送成功的消息最大延迟时间，单位：毫秒",
                new Graph.Target("raven_byte_message_sent_max_delay_milliseconds{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
