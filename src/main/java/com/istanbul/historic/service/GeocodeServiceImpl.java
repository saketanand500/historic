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


@Service
public class GeocodeServiceImpl {


    //final String ROOT_URI = "https://api.mapbox.com/geocoding/v5/mapbox.places/Istanbul.json?limit=10&autocomplete=false&fuzzyMatch=false&types=poi&poi=museums&language=en&access_token=pk.eyJ1Ijoic2FtZWVyc2F1cmFiaCIsImEiOiJja2gzazMzdXMwN2IzMnhueXVnOGRrdnVqIn0.ZWgzcWTY0ycDKXPe3whPrA";
    final String BASE_URL="https://api.mapbox.com/";
    final String END_POINT="geocoding/v5/mapbox.places/";
    final String SEARCH_SUFFIX = ".json";
    final String PARAMETER_REQUIRED ="?limit=10&autocomplete=false&fuzzyMatch=false&types=poi&language=en";
    final String ACCESS_TOKEN="&access_token=pk.eyJ1Ijoic2FtZWVyc2F1cmFiaCIsImEiOiJja2gzazMzdXMwN2IzMnhueXVnOGRrdnVqIn0.ZWgzcWTY0ycDKXPe3whPrA";
    @Autowired
    RestTemplate restTemplate;

    public String getHistoricPlaces(String city) {
        System.out.println(city);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL+END_POINT+city+SEARCH_SUFFIX+PARAMETER_REQUIRED+ACCESS_TOKEN, HttpMethod.GET, entity, String.class);

        List<Itenary> itenaries = new ArrayList<>();

        FeatureCollection featureCollection = new Gson().fromJson(response.getBody(), FeatureCollection.class);
        if (featureCollection != null) {
            if (featureCollection.getFeatures() != null && !featureCollection.getFeatures().isEmpty()) {
                for (Features features : featureCollection.getFeatures()) {
                    String placeName = features.getPlaceName();

                    Properties properties = features.getProperties();
                    if (properties == null) {
                        continue;
                    } else {
                        String category = properties.getCategory();
                        if (category == null || category.isEmpty()) {
                            continue;
                        } else {

                            List<String> categories = new ArrayList<>();
                            Collections.addAll(categories, category.split(", "));
                            if (categories.contains("historic") || categories.contains("museum") || categories.contains("mosque")) {
                                Itenary itenary = new Itenary();
                                String[] name = placeName.split(", ");
                                itenary.setName(name[0]);
                                itenary.setRegion(name[name.length-3]);

                                itenary.setCategories(categories);
                                itenaries.add(itenary);
                            }
                        }
                    }
                }
            }
        }

        return new Gson().toJson(itenaries);

    }
}
