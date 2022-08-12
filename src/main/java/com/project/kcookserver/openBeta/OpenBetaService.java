package com.project.kcookserver.openBeta;

import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.openBeta.dto.ApplicantCreateReq;
import com.project.kcookserver.openBeta.dto.LocationListRes;
import com.project.kcookserver.openBeta.entity.Applicant;
import com.project.kcookserver.openBeta.entity.City;
import com.project.kcookserver.openBeta.entity.Location;
import com.project.kcookserver.openBeta.repository.ApplicantRepository;
import com.project.kcookserver.openBeta.repository.CityRepository;
import com.project.kcookserver.openBeta.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.kcookserver.configure.entity.Status.VALID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OpenBetaService {

    private final ApplicantRepository applicantRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void registerApplicant(ApplicantCreateReq dto) {
        Optional<Applicant> optional = applicantRepository.findByPhoneNumberAndStatus(dto.getPhoneNumber(), VALID);
        if(optional.isPresent()) throw new CustomException(CustomExceptionStatus.APPLICANT_ALREADY_EXIST);

        City city = cityRepository.findByCityIdAndStatus(dto.getCityIndex(), VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.CITY_NOT_FOUND));
        Location location = null;
        if (dto.getLocationIndex() != null) {
            location = locationRepository.findByLocationIdAndStatus(dto.getLocationIndex(), VALID)
                    .orElseThrow(() -> new CustomException(CustomExceptionStatus.LOCATION_NOT_FOUND));
        }
        else {
            throw new CustomException(CustomExceptionStatus.LOCATION_NOT_FOUND);
        }

        Applicant applicant = new Applicant(dto.getPhoneNumber(), city, location);
        applicantRepository.save(applicant);
    }

    public List<LocationListRes> getAllLocationByCity(Integer cityIndex) {
        City city = cityRepository.findByCityIdAndStatus(cityIndex, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.CITY_NOT_FOUND));
        return locationRepository.findAllByStatusAndCity(VALID, city);
    }
}
