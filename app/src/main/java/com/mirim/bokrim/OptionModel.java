package com.mirim.bokrim;

public class OptionModel {
    public String name;
    public int imageResourceID;
    public int accessoryType;
    public int accessoryID;
    public int tint = -1;

    // Figure accessory.
    public OptionModel(int iconResourceID, int accessoryID, int accessoryType) {
        this.imageResourceID = iconResourceID;
        this.accessoryType = accessoryType;
        this.accessoryID = accessoryID;
    }

    public OptionModel(int iconResourceID, int tint, String name) {
        this.imageResourceID = iconResourceID;
        this.tint = tint;
        this.name = name;
    }

    public OptionModel(int iconResourceID, int tint) {
        this.imageResourceID = iconResourceID;
        this.tint = tint;
    }

}