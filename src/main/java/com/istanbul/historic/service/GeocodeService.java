package com.istanbul.historic.service;

import com.google.gson.Gson;
import com.istanbul.historic.model.FeatureCollection;
import com.istanbul.historic.model.Features;
import com.istanbul.historic.model.Itenary;
import com.istanbul.historic.model.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Service dedicated to Geocoding.
 *
 * @author Saket Anand
 */
@Service
public class GeocodeService {

    /*-------------------------------------------------- Constants ---------------------------------------------------*/

    private static final String BASE_URL = "https://api.mapbox.com/";
    private static final String END_POINT = "geocoding/v5/mapbox.places/";
    private static final String SEARCH_SUFFIX = ".json";
    private static final String QUERY_PARAM_LIMIT = "limit";
    private static final String QUESTION = "?";
    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final String QUERY_PARAM_AUTO_COMPLETE = "autocomplete";
    private static final String QUERY_PARAM_FUZZY_MATCH = "fuzzyMatch";
    private static final String QUERY_PARAM_TYPES = "types";
    private static final String QUERY_PARAM_LANGUAGE = "language";
    private static final String QUERY_PARAM_ACCESS_TOKEN = "access_token";

    private static final String CATEGORY_HISTORIC = "historic";
    private static final String CATEGORY_MUSEUM = "museum";
    private static final String CATEGORY_MOSQUE = "mosque";

    /*-------------------------------------------------- Components --------------------------------------------------*/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Gson gson;

    /*------------------------------------------------ Public Methods ------------------------------------------------*/

    /**
     * This method is to get all the historic places using MapBox REST API which is consumed through {@link RestTemplate}.
     *
     * @param city Name of City provided by user.
     * @return JSON String of {@link List} of {@link Itenary}.
     */
    public String getHistoricPlaces(String city) {

        // Create HTTP Entity.
        HttpHeaders headers = new HttpHeaders();
        //noinspection ArraysAsListWithZeroOrOneArgument
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Create HTTP Response from the given URL.
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + END_POINT + city + SEARCH_SUFFIX + QUESTION +
                        QUERY_PARAM_LIMIT + EQUALS + "10" + AND +
                        QUERY_PARAM_AUTO_COMPLETE + EQUALS + "false" + AND +
                        QUERY_PARAM_FUZZY_MATCH + EQUALS + "false" + AND +
                        QUERY_PARAM_TYPES + EQUALS + "poi" + AND +
                        QUERY_PARAM_LANGUAGE + EQUALS + "en" + AND +
                        QUERY_PARAM_ACCESS_TOKEN + EQUALS + "pk.eyJ1Ijoic2FtZWVyc2F1cmFiaCIsImEiOiJja2gzazMzdXMwN2IzMnhueXVnOGRrdnVqIn0.ZWgzcWTY0ycDKXPe3whPrA"
                ,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Extract the Class FeatureCollection out of Response String as JSON.
        FeatureCollection collection;
        try {
            collection = gson.fromJson(response.getBody(), FeatureCollection.class);
        } catch (Exception e) {
            collection = null;
        }

        // Halt the further execution and return an empty list if collection is null.
        if (collection == null) {
            return gson.toJson(new ArrayList<>());
        }

        // Using Gson, convert the reference of List of Itenary to JSON and return it.
        return gson.toJson(collection.extractItenaryOutOfFeatures(gson));

    }

}
