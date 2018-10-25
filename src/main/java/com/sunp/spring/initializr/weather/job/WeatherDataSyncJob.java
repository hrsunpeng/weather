package com.sunp.spring.initializr.weather.job;

import com.sunp.spring.initializr.weather.service.CityDataService;
import com.sunp.spring.initializr.weather.service.WeatherDataService;
import com.sunp.spring.initializr.weather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 定时获取天气数据
 */
public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
         // 获取城市列表
        List<City> listCity = null ;
        try {
              listCity =   cityDataService.listCity();
            // 循环 根据城市列表 添加数据
            if(listCity!=null){
                for (City city: listCity ) {
                    String cityId = city.getCityId();
                    logger.info("城市天气同步,{}",cityId);
                    weatherDataService.syncDataByCityId(cityId);
                }
                logger.info("数据同步完成。。" );
            }
        } catch (Exception e) {
            logger.error("Exception = {}",e.getMessage());
        }
    }
}
