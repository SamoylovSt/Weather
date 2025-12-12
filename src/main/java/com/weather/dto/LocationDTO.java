package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {
    private String name;
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lon")
    private Double longitude;
    private String country;
}
