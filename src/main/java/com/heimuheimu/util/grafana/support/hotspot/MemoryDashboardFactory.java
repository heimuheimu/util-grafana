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

/**
 * JVM 内存使用信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class MemoryDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Memory");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_heap_memory_init_megabytes",
                "当前 heap 内存区域初始化内存大小，单位：MB",
                new Graph.Target("hotspot_heap_memory_init_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_heap_memory_used_megabytes",
                "当前 heap 内存区域正在使用的内存大小，单位：MB",
                new Graph.Target("hotspot_heap_memory_used_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_heap_memory_committed_megabytes",
                "当前 heap 内存区域保证可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_heap_memory_committed_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_heap_memory_max_megabytes",
                "当前 heap 内存区域最大可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_heap_memory_max_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_nonheap_memory_init_megabytes",
                "当前 non-heap 内存区域初始化内存大小，单位：MB",
                new Graph.Target("hotspot_nonheap_memory_init_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_nonheap_memory_used_megabytes",
                "当前 non-heap 内存区域正在使用的内存大小，单位：MB",
                new Graph.Target("hotspot_nonheap_memory_used_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_nonheap_memory_committed_megabytes",
                "当前 non-heap 内存区域保证可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_nonheap_memory_committed_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "hotspot_nonheap_memory_max_megabytes",
                "当前 non-heap 内存区域最大可使用的内存大小，单位：MB",
                new Graph.Target("hotspot_nonheap_memory_max_bytes{job=\"[[job]]\"} / 1024 / 1024", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
