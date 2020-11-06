package com.istanbul.historic.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureCollection {

    /*-------------------------------------------------- Constants ---------------------------------------------------*/

    private static final String CATEGORY_HISTORIC = "historic";
    private static final String CATEGORY_MUSEUM = "museum";
    private static final String CATEGORY_MOSQUE = "mosque";

    /*----------------------------------------------- Member Variables -----------------------------------------------*/

    @SerializedName("features")
    private List<Features> features;

    /*--------------------------------------------- Getters and Setters ----------------------------------------------*/

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

    /*------------------------------------------------ Public Methods ------------------------------------------------*/

    /**
     * Extracts the {@link List} of {@link Itenary} from {@link FeatureCollection#features}.
     *
     * @param gson Instance of {@link Gson}.
     * @return If the {@link FeatureCollection#features} is null or empty, then an empty {@link List} of {@link Itenary},
     * otherwise a populated {@link List} of {@link Itenary}.
     */
    public List<Itenary> extractItenaryOutOfFeatures(Gson gson) {

        // Instantiate the List of Itenary.
        List<Itenary> itenaries = new ArrayList<>();

        // Halt the further execution and return an empty list if the collection does not contain the features.
        if (getFeatures() == null || getFeatures().isEmpty()) {
            return itenaries;
        }

        // Iterate over the List of Features.
        for (Features features : getFeatures()) {

            String placeName = features.getPlaceName();
            Properties properties = features.getProperties();

            // Halt the further execution and proceed to next Feature if the property of this Feature is null.
            if (properties == null) {
                continue;
            }

            String category = properties.getCategory();

            // Halt the further execution and proceed to next Feature if the category of this Feature is not present.
            if (category == null || category.isEmpty()) {
                continue;
            }

            // Instantiate new List of String to store the List of Categories.
            List<String> categories = new ArrayList<>();

            // Using collection, add all the split of category to List of String as 'categories'.
            Collections.addAll(categories, category.split(", "));

            boolean isCategoryHistoric = categories.contains(CATEGORY_HISTORIC);
            boolean isCategoryMuseum = categories.contains(CATEGORY_MUSEUM);
            boolean isCategoryMosque = categories.contains(CATEGORY_MOSQUE);

            // Halt the further execution and proceed to next Feature if the category of this Feature does not matches
            // all of the category we are interested in.
            if (!(isCategoryHistoric || isCategoryMuseum || isCategoryMosque)) {
                continue;
            }

            // From MapBox, we are getting 'placeName' which has comma separated String denoting the Place Name along
            // with it's address.
            // So, we are Splitting this 'placeName' and we are interested in Strings at two locations:
            // 1. First Position denotes name of the Place.
            // 2. Third Last Position denotes the name of the Region.

            String[] name = placeName.split(", ");

            // Create a new instance of Itenary.
            Itenary itenary = new Itenary();

            // Set the Name of Itenary as first position String from split.
            itenary.setName(name[0]);

            // Set the Region of Itenary as third last position String from split.
            itenary.setRegion(name[name.length - 3]);

            // Set all the categories.
            itenary.setCategories(categories);

            // Add this Itenary to list of Itenaries.
            itenaries.add(itenary);

        }

        return itenaries;

    }

}
