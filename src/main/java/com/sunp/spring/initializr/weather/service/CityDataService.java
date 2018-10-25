package com.sunp.spring.initializr.weather.service;

import com.sunp.spring.initializr.weather.vo.City;

import java.util.List;

public interface CityDataService {

    /**
     * 获取city列表
     * @return
     * @throws Exception
     */
    List<City> listCity() throws  Exception;

}
