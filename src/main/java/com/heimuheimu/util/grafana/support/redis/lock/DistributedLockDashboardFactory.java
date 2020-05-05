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

package com.heimuheimu.util.grafana.support.redis.lock;

import com.heimuheimu.util.grafana.dashboard.Dashboard;
import com.heimuheimu.util.grafana.dashboard.panels.Graph;
import com.heimuheimu.util.grafana.dashboard.panels.GridPos;
import com.heimuheimu.util.grafana.dashboard.variables.Constant;

/**
 * Redis 分布式锁信息 dashboard 工厂类。
 *
 * @author heimuheimu
 */
class DistributedLockDashboardFactory {

    public static Dashboard create(String job, String interval) {
        Dashboard dashboard = new Dashboard();
        dashboard.setTitle("DistributedLock");

        dashboard.addVariable(new Constant("interval", interval))
                .addVariable(new Constant("job", job));

        int panelIndex = 0;
        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_success_count",
                "相邻两次采集周期内 Redis 分布式锁获取成功的次数",
                new Graph.Target("naiveredis_distributed_lock_success_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_fail_count",
                "相邻两次采集周期内 Redis 分布式锁获取失败的次数",
                new Graph.Target("naiveredis_distributed_lock_fail_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_error_count",
                "相邻两次采集周期内 Redis 分布式锁获取时出现异常的次数",
                new Graph.Target("naiveredis_distributed_lock_error_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_unlock_success_count",
                "相邻两次采集周期内 Redis 分布式锁释放成功的次数",
                new Graph.Target("naiveredis_distributed_lock_unlock_success_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_avg_holding_time_millisecond",
                "相邻两次采集周期内释放成功的 Redis 分布式锁的平均被持有时间，单位：毫秒",
                new Graph.Target("naiveredis_distributed_lock_avg_holding_time_millisecond{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_max_holding_time_millisecond",
                "相邻两次采集周期内释放成功的 Redis 分布式锁的最大持有时间，单位：毫秒",
                new Graph.Target("naiveredis_distributed_lock_max_holding_time_millisecond{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex++), "$interval"));

        dashboard.addPanel(new Graph((panelIndex + 1) * 2, "naiveredis_distributed_lock_unlock_error_count",
                "相邻两次采集周期内 Redis 分布式锁释放异常的次数",
                new Graph.Target("naiveredis_distributed_lock_unlock_error_count{job=\"[[job]]\"}", "{{instance}}"),
                GridPos.buildForTwoColumns(panelIndex), "$interval"));
        return dashboard;
    }
}
