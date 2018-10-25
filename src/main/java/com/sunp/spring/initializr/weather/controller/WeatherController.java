package com.sunp.spring.initializr.weather.controller;


import com.sunp.spring.initializr.weather.service.WeatherDataService;
import com.sunp.spring.initializr.weather.vo.WeatherResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Resource
    private WeatherDataService weatherDataService;

    @GetMapping("/cityId/{cityId}")
    @ResponseBody
    public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId){
        return weatherDataService.getDataByCityId(cityId);
    }

    @PostMapping("/cityName")
    @ResponseBody
    public WeatherResponse getWeatherByCityName(String cityName){
        return  weatherDataService.getDataByCityName(cityName);
    }



}
