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

package com.heimuheimu.util.grafana.support.hotspot;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * JVM 内存池内存使用信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class MemoryPoolDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("MemoryPool");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("poolName", "内存池", "hotspot_memory_pool_init_bytes{job=\"" + job + "\"}",
                "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_init_megabytes",
                "该内存池区域初始化内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_init_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_used_megabytes",
                "该内存池区域正在使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_used_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_committed_megabytes",
                "该内存池区域保证可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_committed_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_max_megabytes",
                "该内存池区域最大可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_max_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_peak_init_megabytes",
                "相邻两次采集周期内该内存池区域达到使用峰值时的初始化内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_peak_init_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_peak_used_megabytes",
                "相邻两次采集周期内该内存池区域达到使用峰值时的使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_peak_used_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_peak_committed_megabytes",
                "相邻两次采集周期内该内存池区域达到使用峰值时的保证可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_peak_committed_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_memory_pool_peak_max_megabytes",
                "相邻两次采集周期内该内存池区域达到使用峰值时的最大可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_memory_pool_peak_max_bytes{name=\"[[poolName]]\",job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
