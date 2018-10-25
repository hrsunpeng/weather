package com.sunp.spring.initializr.weather.service;

import com.sunp.spring.initializr.weather.vo.WeatherResponse;

public interface WeatherDataService {

    public WeatherResponse getDataByCityId(String cityId);

    public WeatherResponse getDataByCityName(String cityName);

    /**
     * 根据城市ID 同步天气数据方法
     * @param cityId
     */
    void syncDataByCityId(String cityId);

}
