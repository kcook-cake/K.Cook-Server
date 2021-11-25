package com.project.kcookserver.openBeta.dto;

import com.project.kcookserver.openBeta.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationListRes {

    Integer locationId;
    String locationName;

    public LocationListRes(Location location) {
        this.locationId = location.getLocationId();
        this.locationName = location.getName();
    }
}
