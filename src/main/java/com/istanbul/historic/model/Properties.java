package com.istanbul.historic.model;

import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("category")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
