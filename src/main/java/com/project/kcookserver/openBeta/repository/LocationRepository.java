package com.project.kcookserver.openBeta.repository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.openBeta.dto.LocationListRes;
import com.project.kcookserver.openBeta.entity.City;
import com.project.kcookserver.openBeta.entity.Location;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByLocationIdAndStatus(Integer id, Status status);
    List<LocationListRes> findAllByStatusAndCity(Status status, City city);
}
