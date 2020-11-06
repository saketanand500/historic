package com.istanbul.historic.model;

import com.google.gson.annotations.SerializedName;

public class Properties {

    /*----------------------------------------------- Member Variables -----------------------------------------------*/

    @SerializedName("category")
    private String category;

    /*--------------------------------------------- Getters and Setters ----------------------------------------------*/

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
