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

package com.heimuheimu.util.grafana.folder;

/**
 * Grafana 中的文件夹信息。
 *
 * <p><strong>说明：</strong>Folder 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class Folder {

    /**
     * 文件夹 ID
     */
    private int id = -1;

    /**
     * 文件夹名称
     */
    private String title = "";

    /**
     * 获得文件夹 ID。
     *
     * @return 文件夹 ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置文件夹 ID。
     *
     * @param id 文件夹 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获得文件夹名称。
     *
     * @return 文件夹名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文件夹名称。
     *
     * @param title 文件夹名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
