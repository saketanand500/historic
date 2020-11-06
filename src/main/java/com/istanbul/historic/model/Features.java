package com.istanbul.historic.model;

import com.google.gson.annotations.SerializedName;

public class Features {

    /*----------------------------------------------- Member Variables -----------------------------------------------*/

    @SerializedName("properties")
    private Properties properties;

    @SerializedName("place_name")
    private String placeName;

    /*--------------------------------------------- Getters and Setters ----------------------------------------------*/

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
