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

package com.heimuheimu.util.grafana.support.async.consumer;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;

/**
 * 异步消息消费者聚合信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class OverviewDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Overview");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveasync_consumer_polled_count:sum",
                "相邻两次采集周期内已拉取的消息总数，根据项目名称进行聚合计算",
                new Graph.Target("job:naiveasync_consumer_polled_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveasync_consumer_success_count:sum",
                "相邻两次采集周期内已消费成功的消息总数，根据项目名称进行聚合计算",
                new Graph.Target("job:naiveasync_consumer_success_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveasync_consumer_max_delay_milliseconds:max",
                "相邻两次采集周期内消息到达最大延迟时间（消息延迟时间 = 消息拉取时间 - 消息发送时间），单位：毫秒，根据项目名称进行聚合计算",
                new Graph.Target("job:naiveasync_consumer_max_delay_milliseconds:max{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveasync_consumer_avg_delay_milliseconds:avg",
                "相邻两次采集周期内消息到达平均延迟时间（消息延迟时间 = 消息拉取时间 - 消息发送时间），单位：毫秒，根据项目名称进行聚合计算",
                new Graph.Target("job:naiveasync_consumer_avg_delay_milliseconds:avg{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "job:naiveasync_consumer_exec_error_count:sum",
                "相邻两次采集周期内消费出错次数，包含 Kafka 操作出现的错误和消费过程中出现的错误，根据项目名称进行聚合计算",
                new Graph.Target("job:naiveasync_consumer_exec_error_count:sum{job=\"[[job]]\"}", "{{job}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
