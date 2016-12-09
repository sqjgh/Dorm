package com.example.dllo.dorm.mainpage.swipecards;

import java.util.List;

/**
 * Created by zhaojun on 16/11/22.
 */
public class CardMode {
    private String name;
    private int year;
    private List<String> images;

    public CardMode(String name, int year, List<String> images) {
        this.name = name;
        this.year = year;
        this.images = images;
    }

    public CardMode() {
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }
}
