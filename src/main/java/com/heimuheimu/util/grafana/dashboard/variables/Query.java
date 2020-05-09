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

package com.heimuheimu.util.grafana.dashboard.variables;

import com.heimuheimu.util.grafana.dashboard.Variable;

/**
 * 类型为 "query" 的变量信息。
 *
 * <p><strong>说明：</strong>Query 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class Query implements Variable {

    /**
     * 数据源名称
     */
    private String datasource;

    /**
     * 变量名称
     */
    private String name;

    /**
     * 变量显示名称
     */
    private String label;

    /**
     * 变量值查询语句
     */
    private String query;

    /**
     * 变量值过滤正则表达式
     */
    private String regex;

    /**
     * 变量值排序方式，默认为：Alphabetical(case-insensitive,asc)
     */
    private int sort = 5;

    /**
     * 变量刷新方式，默认为：On Dashboard Load
     */
    private int refresh = 1;

    /**
     * 构造一个 Query 实例。
     *
     * @param name 变量名称
     * @param label 变量显示名称
     * @param query 变量值查询语句
     * @param regex 变量值过滤正则表达式
     * @param datasource 数据源名称
     */
    public Query(String name, String label, String query, String regex, String datasource) {
        this.name = name;
        this.label = label;
        this.query = query;
        this.regex = regex;
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
     * 获得变量名称。
     *
     * @return 变量名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置变量名称。
     *
     * @param name 变量名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得变量显示名称。
     *
     * @return 变量显示名称
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置变量显示名称。
     *
     * @param label 变量显示名称
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 获得变量值查询语句。
     *
     * @return 变量值查询语句
     */
    public String getQuery() {
        return query;
    }

    /**
     * 设置变量值查询语句。
     *
     * @param query 变量值查询语句
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 获得变量值过滤正则表达式。
     *
     * @return 变量值过滤正则表达式
     */
    public String getRegex() {
        return regex;
    }

    /**
     * 设置变量值过滤正则表达式。
     *
     * @param regex 变量值过滤正则表达式
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * 获得变量值排序方式，默认为：Alphabetical(case-insensitive,asc)
     *
     * @return 变量值排序方式
     */
    public int getSort() {
        return sort;
    }

    /**
     * 设置变量值排序方式。
     *
     * @param sort 变量值排序方式
     */
    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * 获得变量刷新方式，默认为：On Dashboard Load。
     *
     * @return 变量刷新方式
     */
    public int getRefresh() {
        return refresh;
    }

    /**
     * 设置变量刷新方式。
     *
     * @param refresh 变量刷新方式
     */
    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    @Override
    public String getType() {
        return "query";
    }

    @Override
    public String toString() {
        return "Query{" +
                "datasource='" + datasource + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", query='" + query + '\'' +
                ", regex='" + regex + '\'' +
                ", sort=" + sort +
                ", refresh=" + refresh +
                '}';
    }
}
