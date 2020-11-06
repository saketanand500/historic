package com.istanbul.historic.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Itenary {

    @SerializedName("name")
    private String name;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("region")
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


}
