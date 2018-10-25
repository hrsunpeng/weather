package com.sunp.spring.initializr.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunp.spring.initializr.weather.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);


    private static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";

    private  static final long TIME_OUT = 1800L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String url = WEATHER_URL + "citykey="+  cityId ;
        return this.doGetWeather(url);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String url = WEATHER_URL + "city="+  cityName ;
        return this.doGetWeather(url);
    }



    private WeatherResponse doGetWeather(String url){
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null ;
        String strBody = null;

        ValueOperations<String, String> ops =  stringRedisTemplate.opsForValue();

        if (stringRedisTemplate.hasKey(url)){
            logger.info("Redis has Key = {}",url);
            // 如果缓存中存在
            strBody  =  ops.get(url);

        }else{
            logger.info("第三方数据查询 = {}",url);
            // 如果不存在
            ResponseEntity<String> respStr = restTemplate.getForEntity(url,String.class);
            if (respStr.getStatusCodeValue() == 200){
                strBody = respStr.getBody();
            }
            // 把数据写入到缓存中
            ops.set(url,strBody,TIME_OUT, TimeUnit.SECONDS);
        }

        try{
            resp =  mapper.readValue(strBody,WeatherResponse.class);
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
        return resp ;
    }



    @Override
    public void syncDataByCityId(String cityId) {
        String url = WEATHER_URL + "citykey="+  cityId ;
        this.saveWeatherData(url);
    }


    /**
     * 把天气数据放到缓存中
     * @param url
     */
    private void saveWeatherData(String url){
        String strBody = null;
        ValueOperations<String, String> ops =  stringRedisTemplate.opsForValue();
        // 如果不存在
        ResponseEntity<String> respStr = restTemplate.getForEntity(url,String.class);
        if (respStr.getStatusCodeValue() == 200){
            strBody = respStr.getBody();
        }
        // 把数据写入到缓存中
        ops.set(url,strBody,TIME_OUT, TimeUnit.SECONDS);
    }



}
