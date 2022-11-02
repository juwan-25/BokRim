package com.mirim.ListView;

public class ListViewItem {
    private String name;
    private String location ;
    private int id;

    public void setTitle(String name) {
        this.name = name ;
    }
    public void setDesc(String desc) {
        this.location = desc ;
    }
    public void setId(int id) {
        this.id = id ;
    }

    public String getTitle() {
        return this.name;
    }
    public String getDesc() {
        return this.location;
    }
    public int getId() {
        return this.id;
    }
}