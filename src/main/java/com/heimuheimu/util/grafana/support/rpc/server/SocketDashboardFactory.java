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

package com.heimuheimu.util.grafana.support.rpc.server;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;
import com.heimuheimu.util.grafana.dashboard.variables.Query;

/**
 * RPC 服务端 Socket 读、写信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class SocketDashboardFactory {

    public static Dashboard create(String job, String interval, String datasource) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("Socket");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job))
                .addVariable(new Query("name", "RPC 服务名称",
                        "instance:naiverpc_server_socket_read_count:sum{job=\"" + job + "\"}", "/.*name=\"([^\"]*).*/", datasource));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_read_count:sum",
                "相邻两次采集周期内 Socket 读取的次数，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_read_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_read_megabytes:sum",
                "相邻两次采集周期内 Socket 读取的字节总数，单位：MB，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_read_megabytes:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_max_read_bytes:max",
                "相邻两次采集周期内单次 Socket 读取的最大字节数，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_max_read_bytes:max{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_write_count:sum",
                "相邻两次采集周期内 Socket 写入的次数，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_write_count:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_write_megabytes:sum",
                "相邻两次采集周期内 Socket 写入的字节总数，单位：MB，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_write_megabytes:sum{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval", datasource));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "instance:naiverpc_server_socket_max_write_bytes:max",
                "相邻两次采集周期内单次 Socket 写入的最大字节数，根据项目名称、主机地址和 RPC 服务名称进行聚合计算",
                new Graph.Target("instance:naiverpc_server_socket_max_write_bytes:max{name=~\"[[name]]\",job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval", datasource));
        return dashboard;
    }
}
