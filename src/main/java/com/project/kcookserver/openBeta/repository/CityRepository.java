package com.project.kcookserver.openBeta.repository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.openBeta.dto.CityListRes;
import com.project.kcookserver.openBeta.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByCityIdAndStatus(Integer id, Status status);
    List<CityListRes> findAllByStatus(Status status);
}
