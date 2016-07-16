package com.vincent.hhxz.bean;

import java.util.List;

/**
 * Created by CJ on 2016/7/2.
 */
public class HotSearchBean {


    /**
     * end : 12
     * total : 12
     * hots : ["新西兰","红枣","牛肉干","礼盒","绿茶","柠檬","薏米","黑芝麻","花草茶","澳洲","野生蜂蜜","零食"]
     */

    private int end;
    private int total;
    private List<String> hots;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getHots() {
        return hots;
    }

    public void setHots(List<String> hots) {
        this.hots = hots;
    }
}
