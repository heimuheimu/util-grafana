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

/**
 * 图表位置信息。
 *
 * <p><strong>说明：</strong>GridPos 类是非线程安全的，不允许多个线程使用同一个实例。</p>
 *
 * @author heimuheimu
 */
public class GridPos {

    /**
     * 图表高度
     */
    private int h;

    /**
     * 图表宽度
     */
    private int w;

    /**
     * 图表所在 X 轴坐标
     */
    private int x;

    /**
     * 图表所在 Y 轴坐标
     */
    private int y;

    /**
     * 构造一个 GridPos 实例。
     *
     * @param h 图表高度
     * @param w 图表宽度
     * @param x 图表所在 X 轴坐标
     * @param y 图表所在 Y 轴坐标
     */
    public GridPos(int h, int w, int x, int y) {
        this.h = h;
        this.w = w;
        this.x = x;
        this.y = y;
    }

    /**
     * 获得图表高度。
     *
     * @return 图表高度
     */
    public int getH() {
        return h;
    }

    /**
     * 设置图表高度。
     *
     * @param h 图表高度
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * 获得图表宽度。
     *
     * @return 图表宽度
     */
    public int getW() {
        return w;
    }

    /**
     * 设置图表宽度。
     *
     * @param w 图表宽度
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * 获得图表所在 X 轴坐标。
     *
     * @return 图表所在 X 轴坐标
     */
    public int getX() {
        return x;
    }

    /**
     * 设置图表所在 X 轴坐标。
     *
     * @param x 图表所在 X 轴坐标
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 获得图表所在 Y 轴坐标。
     *
     * @return 图表所在 Y 轴坐标
     */
    public int getY() {
        return y;
    }

    /**
     * 设置图表所在 Y 轴坐标。
     *
     * @param y 图表所在 Y 轴坐标
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 根据图表索引位置（从 0 开始）计算图表位置信息，每行两张图表。
     *
     * @param panelIndex  图表索引位置（从 0 开始）
     * @return 图表位置信息
     */
    public static GridPos buildForTwoColumns(int panelIndex) {
        return new GridPos(8, 12, panelIndex % 2 == 0 ? 0 : 12, (panelIndex / 2) * 8);
    }

    @Override
    public String toString() {
        return "GridPos{" +
                "h=" + h +
                ", w=" + w +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
