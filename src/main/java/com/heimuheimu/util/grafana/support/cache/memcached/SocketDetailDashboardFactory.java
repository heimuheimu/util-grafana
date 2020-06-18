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

package com.heimuheimu.util.grafana.support.cache.memcached;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * Memcached 客户端 Socket 读、写详情信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class SocketDetailDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("SocketDetail");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "Memcached 集群名称",
                        "naivecache_memcached_socket_read_count{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource))
                .addVariable(new Query("instance", "主机地址",
                        "naivecache_memcached_socket_read_count{job=\"" + job + "\"}", "/.*instance=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_read_count",
                "相邻两次采集周期内 Socket 读取的次数",
                new Graph.Target("naivecache_memcached_socket_read_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_read_bytes",
                "相邻两次采集周期内 Socket 读取的字节总数",
                new Graph.Target("naivecache_memcached_socket_read_bytes{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_max_read_bytes",
                "相邻两次采集周期内单次 Socket 读取的最大字节数",
                new Graph.Target("naivecache_memcached_socket_max_read_bytes{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_write_count",
                "相邻两次采集周期内 Socket 写入的次数",
                new Graph.Target("naivecache_memcached_socket_write_count{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_write_bytes",
                "相邻两次采集周期内 Socket 写入的字节总数",
                new Graph.Target("naivecache_memcached_socket_write_bytes{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naivecache_memcached_socket_max_write_bytes",
                "相邻两次采集周期内单次 Socket 写入的最大字节数",
                new Graph.Target("naivecache_memcached_socket_max_write_bytes{name=~\"[[name]]\",instance=~\"[[instance]]\",job=\"[[job]]\"}", "{{remoteAddress}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
