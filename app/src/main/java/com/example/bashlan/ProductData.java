package com.example.bashlan;

public class ProductData {

    private String name;
    private int imagePosition;
    private boolean having;

    public ProductData() {
    }

    public ProductData(String name, int imagePosition, boolean having) {
        this.name = name;
        this.imagePosition = imagePosition;
        this.having = having;
    }

    public String getName() {
        return name;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public boolean isHaving() {
        return having;
    }

    public void setHaving(boolean having) {
        this.having = having;
    }

}
