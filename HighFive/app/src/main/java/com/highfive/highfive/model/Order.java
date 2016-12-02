package com.highfive.highfive.model;

import java.util.Date;

/**
 * Created by dan on 02.12.16.
 */

public class Order {
    private int orderdId;
    private String theme;
    private String description;
    private Date date;

    public Order(String themeId, String description) {
        this.theme = themeId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderdId() {
        return orderdId;
    }

    public void setOrderdId(int orderdId) {
        this.orderdId = orderdId;
    }
}
