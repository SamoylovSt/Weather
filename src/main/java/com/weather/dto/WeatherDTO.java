package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    private String city;

    private double temperature;


    private double feelsLike;

    private int humidity;

    private String description;


    private String country;

    public WeatherDTO() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    @JsonSetter("name")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonSetter("main")
    public void setMain(Map<String, Object> main) {
        if (main == null) {
            return;
        }
        if (main.containsKey("temp")) {
            this.temperature = ((Number) main.get("temp")).doubleValue();
        }
        if (main.containsKey("feels_like")) {
            this.feelsLike = ((Number) main.get("feels_like")).doubleValue();
        }
        if (main.containsKey("humidity")) {
            this.humidity = ((Number) main.get("humidity")).intValue();
        }
    }

    @JsonSetter("weather")
    public void setWeather(List<Map<String, Object>> weather) {
        if (weather == null || weather.isEmpty()) {
            return;
        }
        Map<String, Object> firstWeather = weather.get(0);
        if (firstWeather.containsKey("description")) {
            Object desc = firstWeather.get("description").toString();
            if (desc != null) {
                this.description = desc.toString();
            }
        }
    }

    @JsonSetter("sys")
    public void setCountry(Map<String, Object> sys) {
        if (sys == null) {
            return;
        }
        if (sys.containsKey("country")) {
            this.country = sys.get("country").toString();
        }

    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
                "city='" + city + '\'' +
                ", temperature=" + temperature +
                ", feelsLike=" + feelsLike +
                ", humidity=" + humidity +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
