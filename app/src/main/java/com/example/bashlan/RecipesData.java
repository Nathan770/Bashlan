package com.example.bashlan;

public class RecipesData {
    private int id;
    private String title;
    private String image;
    private int missedIngredientCount;

    public RecipesData() {
    }

    public RecipesData(int id, String title, String image, int missedIngredientCount) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.missedIngredientCount = missedIngredientCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }
}
