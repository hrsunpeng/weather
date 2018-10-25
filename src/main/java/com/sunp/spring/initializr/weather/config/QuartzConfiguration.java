package com.sunp.spring.initializr.weather.config;


import com.sunp.spring.initializr.weather.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    private static final  int TIME = 1800;

    //JobDetail
    @Bean
    public JobDetail WeatherDataSyncJobDetail(){
       return JobBuilder.newJob(WeatherDataSyncJob.class)
               .withIdentity("weatherDataSyncJob").storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger WeatherDataSyncTirgger(){

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();

        return  TriggerBuilder.newTrigger().forJob(WeatherDataSyncJobDetail())
                .withIdentity("weatherDataSyncTirgger").withSchedule(scheduleBuilder).build();
    }

}
