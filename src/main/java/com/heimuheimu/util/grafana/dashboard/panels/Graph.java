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

package com.heimuheimu.util.grafana.dashboard.panels;

import com.heimuheimu.util.grafana.dashboard.Panel;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型为 "graph" 的图表信息。
 *
 * <p><strong>说明：</strong>Graph 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class Graph implements Panel {

    /**
     * 数据源名称
     */
    private String datasource;

    /**
     * 图表信息 ID
     */
    private int id;

    /**
     * 图表信息标题
     */
    private String title;

    /**
     * 图表描述信息
     */
    private String description;

    /**
     * 图表目标数据信息列表
     */
    private List<Target> targets = new ArrayList<>();

    /**
     * 图表位置信息
     */
    private GridPos gridPos;

    /**
     * 图表目标数据获取周期，例如：30s、1m ...
     */
    private String interval;

    /**
     * 构造一个 Graph 实例。
     *
     * @param id 图表信息 ID
     * @param title 图表信息标题
     * @param description 图表描述信息
     * @param target 图表目标数据信息
     * @param gridPos 图表位置信息
     * @param interval 图表目标数据获取周期，例如：30s、1m ...
     * @param datasource 数据源名称
     */
    public Graph(int id, String title, String description, Target target, GridPos gridPos, String interval, String datasource) {
        this.id = id;
        this.title = title;
        this.description = description;
        targets.add(target);
        this.gridPos = gridPos;
        this.interval = interval;
        this.datasource = datasource;
    }

    /**
     * 获得数据源名称。
     *
     * @return 数据源名称
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * 设置数据源名称。
     *
     * @param datasource 数据源名称
     */
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    /**
     * 获得图表信息 ID。
     *
     * @return 图表信息 ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置图表信息 ID。
     *
     * @param id 图表信息 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获得图表信息标题。
     *
     * @return 图表信息标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置图表信息标题。
     *
     * @param title 图表信息标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获得图表描述信息。
     *
     * @return 图表描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置图表描述信息。
     *
     * @param description 图表描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获得图表目标数据信息列表。
     *
     * @return 图表目标数据信息列表
     */
    public List<Target> getTargets() {
        return targets;
    }

    /**
     * 设置图表目标数据信息列表。
     *
     * @param targets 图表目标数据信息列表
     */
    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    /**
     * 获得图表位置信息。
     *
     * @return 图表位置信息
     */
    public GridPos getGridPos() {
        return gridPos;
    }

    /**
     * 设置图表位置信息。
     *
     * @param gridPos 图表位置信息
     */
    public void setGridPos(GridPos gridPos) {
        this.gridPos = gridPos;
    }

    /**
     * 获得图表目标数据获取周期，例如：30s、1m ...
     *
     * @return 图表目标数据获取周期
     */
    public String getInterval() {
        return interval;
    }

    /**
     * 设置图表目标数据获取周期，例如：30s、1m ...
     *
     * @param interval 图表目标数据获取周期
     */
    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public String getType() {
        return "graph";
    }

    @Override
    public String toString() {
        return "Graph{" +
                "datasource='" + datasource + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", targets=" + targets +
                ", gridPos=" + gridPos +
                ", interval='" + interval + '\'' +
                '}';
    }

    /**
     * Graph 图表目标数据信息。
     *
     * <p><strong>说明：</strong>Target 类是非线程安全的，不允许多个线程使用同一个实例。</p>
     *
     * @author heimuheimu
     */
    public static class Target {

        /**
         * 目标数据查询语句
         */
        private String expr;

        /**
         * Legend 格式
         */
        private String legendFormat;

        /**
         * 构造一个 Target 实例。
         *
         * @param expr 目标数据查询语句
         * @param legendFormat Legend 格式
         */
        public Target(String expr, String legendFormat) {
            this.expr = expr;
            this.legendFormat = legendFormat;
        }

        /**
         * 获得目标数据查询语句。
         *
         * @return 目标数据查询语句
         */
        public String getExpr() {
            return expr;
        }

        /**
         * 设置目标数据查询语句。
         *
         * @param expr 目标数据查询语句
         */
        public void setExpr(String expr) {
            this.expr = expr;
        }

        /**
         * 获得 Legend 格式。
         *
         * @return Legend 格式
         */
        public String getLegendFormat() {
            return legendFormat;
        }

        /**
         * 设置 Legend 格式。
         *
         * @param legendFormat Legend 格式
         */
        public void setLegendFormat(String legendFormat) {
            this.legendFormat = legendFormat;
        }

        @Override
        public String toString() {
            return "Target{" +
                    "expr='" + expr + '\'' +
                    ", legendFormat='" + legendFormat + '\'' +
                    '}';
        }
    }
}
