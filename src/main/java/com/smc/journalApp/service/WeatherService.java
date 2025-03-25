package com.smc.journalApp.service;

import com.smc.journalApp.api.response.WeatherResponse;
import com.smc.journalApp.cache.AppCache;
import com.smc.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate; //used to access external apis

    @Autowired
    private AppCache appCache;

    @Value("${weather.api.key}") //@Value used to fetch the values written in properties file
    private String apikey;

  //  private String API = ;



    /*
     //Use below for post call to external api
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("key", "value");
    User user = User.builder().userName("Vipul").password ("Vipul").build();
    HttpEntity<User> httpEntity = new HttpEntity<> (user, httpHeaders);

     */

    public WeatherResponse getWeather(String city){

        String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);

        //Use below for post call to external api
        // ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, null, WeatherResponse.class);


        return response.getBody();

    }




}
