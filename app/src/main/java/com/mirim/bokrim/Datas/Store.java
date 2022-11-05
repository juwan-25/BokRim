package com.mirim.bokrim.Datas;

public class Store{
    public int id;
    public String title;
    public String address;
    public Double lat;
    public Double lug;
    public String opertime;
    public String page;
    public String dial;

    public Store(){};

    public Store(int id, String title, String address, Double lat, Double lug, String opertime, String page, String dial) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.lat = lat;
        this.lug = lug;
        this.opertime = opertime;
        this.page = page;
        this.dial = dial;
    }
}