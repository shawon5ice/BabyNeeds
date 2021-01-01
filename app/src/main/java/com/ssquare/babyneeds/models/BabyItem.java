package com.ssquare.babyneeds.models;

public class BabyItem {
    private int id;
    private String itemName;
    private int itemQuantity;
    private String itemColor;
    private String itemSize;
    private String itemAddedDate;

    public BabyItem(int id, String itemName, int itemQuantity, String itemColor, String itemSize, String itemAddedDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemAddedDate = itemAddedDate;
    }

    public BabyItem(String itemName, int itemQuantity, String itemColor, String itemSize, String itemAddedDate) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemAddedDate = itemAddedDate;
    }

    public BabyItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemAddedDate() {
        return itemAddedDate;
    }

    public void setItemAddedDate(String itemAddedDate) {
        this.itemAddedDate = itemAddedDate;
    }
}
