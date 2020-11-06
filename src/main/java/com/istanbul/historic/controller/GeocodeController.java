package com.istanbul.historic.controller;

import com.istanbul.historic.service.GeocodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/myapp")
public class GeocodeController {

    @Autowired
    GeocodeServiceImpl geocodeService;

    @GetMapping("/")
    public String  getHistoricPlace(@RequestParam String city){
        return geocodeService.getHistoricPlaces(city);
         }
}
