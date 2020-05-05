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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类型为 "constant" 的变量信息。
 *
 * <p><strong>说明：</strong>Constant 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class Constant implements Variable {

    /**
     * 变量名称
     */
    private String name;

    /**
     * 变量值查询语句
     */
    private String query;

    /**
     * 可以选择的变量选项列表
     */
    private List<Map<String, Object>> options = new ArrayList<>();

    /**
     * 当前选中的变量
     */
    private Map<String, Object> current = new HashMap<>();

    /**
     * 构造一个 Constant 实例。
     *
     * @param name 变量名称
     * @param value 变量值
     */
    public Constant(String name, String value) {
        this.name = name;
        this.query = value;
        current.put("text", value);
        current.put("value", value);

        Map<String, Object> option = new HashMap<>();
        option.put("selected", true);
        option.put("text", value);
        option.put("value", value);
        options.add(option);
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
     * 获得变量查询语句。
     *
     * @return 获得变量查询语句
     */
    public String getQuery() {
        return query;
    }

    /**
     * 设置变量查询语句。
     *
     * @param query 变量查询语句
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 获得可以选择的变量选项列表。
     *
     * @return 可以选择的变量选项列表
     */
    public List<Map<String, Object>> getOptions() {
        return options;
    }

    /**
     * 设置可以选择的变量选项列表。
     *
     * @param options 可以选择的变量选项列表
     */
    public void setOptions(List<Map<String, Object>> options) {
        this.options = options;
    }

    /**
     * 获得当前选中的变量。
     *
     * @return 当前选中的变量
     */
    public Map<String, Object> getCurrent() {
        return current;
    }

    /**
     * 设置当前选中的变量。
     *
     * @param current 当前选中的变量
     */
    public void setCurrent(Map<String, Object> current) {
        this.current = current;
    }

    @Override
    public String getType() {
        return "constant";
    }

    @Override
    public String toString() {
        return "Constant{" +
                "name='" + name + '\'' +
                ", query='" + query + '\'' +
                ", options=" + options +
                ", current=" + current +
                '}';
    }
}
