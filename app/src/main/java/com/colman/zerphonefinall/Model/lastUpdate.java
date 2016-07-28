package com.colman.zerphonefinall.Model;

/**
 * Created by Sagi on 7/26/2016.
 */
public class lastUpdate {
    private String date;
    private String tableName;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {

        return tableName;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public lastUpdate(String date,String name) {
        this.date = date;
        this.tableName = name;
    }
}
