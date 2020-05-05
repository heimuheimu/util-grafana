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

package com.heimuheimu.util.grafana;

import java.io.OutputStream;

/**
 * 某一类型的 dashboards 生成器，例如：mysql-jdbc、naiveredis...
 *
 * @author heimuheimu
 */
public interface DashboardsBuilder {

    /**
     * 当前生成器是否支持此类型。
     *
     * @param type 类型
     * @return 是否支持
     */
    boolean isSupported(String type);

    /**
     * 在指定的组织中创建对应的 dashboards，如果组织不存在，将会新建组织。
     *
     * @param organizationName dashboards 所在组织名称，与 Prometheus 中的 job 名称一致，不允许为空或 {@code null}
     * @param interval 图表目标数据获取周期，例如：30s、1m ...
     * @param outputStream 用于输出创建信息的输出流，允许为 {@code null}
     * @throws IllegalArgumentException 如果 organizationName 为空或 {@code null}，将会抛出此异常
     * @throws RuntimeException 如果创建过程中出现错误，将会抛出此异常
     */
    void build(String organizationName, String interval, OutputStream outputStream) throws RuntimeException;
}