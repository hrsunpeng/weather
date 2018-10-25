package com.sunp.spring.initializr.weather.service;

import com.sunp.spring.initializr.weather.vo.Weather;

public interface WeatherReportService {

    /**
     * 根据城市ID 查询该城市的天气预报
     * @param cityId
     * @return
     */
    Weather getDataByCityId(String cityId);

}
