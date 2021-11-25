package com.project.kcookserver.openBeta.dto;

import com.project.kcookserver.openBeta.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityListRes {

    Integer cityId;
    String cityName;

    public CityListRes(City city) {
        this.cityId = city.getCityId();
        this.cityName = city.getName();
    }
}
