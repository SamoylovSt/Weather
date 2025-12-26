package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    private String city;

    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private int humidity;
    private String description;
    private String country;
    private String icon;

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

    public BigDecimal getFeelsLike() {
        return this.feelsLike;
    }

    public void setFeelsLike(BigDecimal feelsLike) {
        this.feelsLike = feelsLike;
    }

    public BigDecimal getTemperature() {
        return this.temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon() {
        this.icon = icon.toString();
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
            this.temperature = BigDecimal.valueOf((Double) main.get("temp"));
        }
        if (main.containsKey("feels_like")) {
            this.feelsLike = BigDecimal.valueOf((Double) main.get("feels_like"));
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
        if (firstWeather.containsKey("icon")) {
            Object icon = firstWeather.get("icon");
            if (icon != null) {
                this.icon = icon.toString();
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
