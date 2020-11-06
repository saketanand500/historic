package com.istanbul.historic.controller;

import com.istanbul.historic.service.GeocodeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/myapp")
public class GeocodeController {

    /*-------------------------------------------------- Components --------------------------------------------------*/

    @Autowired
    GeocodeService geocodeService;

    /*------------------------------------------------ HTTP Mappings -------------------------------------------------*/

    @GetMapping("/")
    public String getHistoricPlace(@RequestParam String city) {
        return geocodeService.getHistoricPlaces(city);
    }

}
