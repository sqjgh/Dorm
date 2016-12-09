package com.example.dllo.dorm.right.game2048;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by zhaojun on 16/12/2.
 */
public class ChartBean {

    //每个数据库都应该有必不可少的id  而这个就是给数据库加id的  id可以写get,set方法 也可以不写
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String name;
    private int chart;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChart() {
        return chart;
    }

    public void setChart(int chart) {
        this.chart = chart;
    }
}
