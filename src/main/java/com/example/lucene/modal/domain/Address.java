package com.example.lucene.modal.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @NotNull
    private String id;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String country;
    private String airportName;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private Boolean isActive;
    @JsonIgnore
    private Map<String, Object> timeline;
    @NotNull
    private String stateShort;
    private String iata;
}
