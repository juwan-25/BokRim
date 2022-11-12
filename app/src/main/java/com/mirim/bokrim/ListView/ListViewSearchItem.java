package com.mirim.bokrim.ListView;

public class ListViewSearchItem {
    //TODO: ImageView 추가
    private String name;
    private String location ;
    private int img;
    private int id;

    public void setTitle(String name) {
        this.name = name ;
    }
    public void setDesc(String desc) {
        this.location = desc ;
    }
    public void setImg(int img) {
        this.img = img ;
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
    public int getImg() {
        return this.img;
    }
    public int getId() {
        return this.id;
    }
}