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

package com.heimuheimu.util.grafana.datasource;

/**
 * Grafana 中的数据源信息。
 *
 * <p><strong>说明：</strong>DataSource 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class DataSource {

    /**
     * 数据源类型：Prometheus
     */
    public final static String TYPE_PROMETHEUS = "prometheus";

    /**
     * 数据源访问模式：proxy
     */
    public final static String ACCESS_MODE_PROXY = "proxy";

    /**
     * 数据源 ID
     */
    private int id = -1;

    /**
     * 数据源名称
     */
    private String name = "";

    /**
     * 数据源类型，默认为："prometheus"
     */
    private String type = TYPE_PROMETHEUS;

    /**
     * 数据源 URL 地址
     */
    private String url = "";

    /**
     * 数据源访问模式，默认为："proxy"
     */
    private String access = ACCESS_MODE_PROXY;

    /**
     * 获得数据源 ID。
     *
     * @return 数据源 ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置数据源 ID。
     *
     * @param id 数据源 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获得数据源名称。
     *
     * @return 数据源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据源名称。
     *
     * @param name 数据源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得数据源类型，默认为："prometheus"。
     *
     * @return 数据源类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置数据源类型。
     *
     * @param type 数据源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获得数据源 URL 地址。
     *
     * @return 数据源 URL 地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置数据源 URL 地址。
     *
     * @param url 数据源 URL 地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获得数据源访问模式，默认为："proxy"。
     *
     * @return 数据源访问模式
     */
    public String getAccess() {
        return access;
    }

    /**
     * 设置数据源访问模式。
     *
     * @param access 数据源访问模式
     */
    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", access='" + access + '\'' +
                '}';
    }
}
