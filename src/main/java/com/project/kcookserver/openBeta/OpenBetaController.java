package com.project.kcookserver.openBeta;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.openBeta.dto.ApplicantCreateReq;
import com.project.kcookserver.openBeta.dto.ApplicantListRes;
import com.project.kcookserver.openBeta.dto.CityListRes;
import com.project.kcookserver.openBeta.dto.LocationListRes;
import com.project.kcookserver.openBeta.entity.City;
import com.project.kcookserver.openBeta.repository.ApplicantRepository;
import com.project.kcookserver.openBeta.repository.CityRepository;
import com.project.kcookserver.openBeta.repository.LocationRepository;
import com.project.kcookserver.util.ValidationExceptionProvider;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.project.kcookserver.configure.entity.Status.*;

@Api(tags = {"OpenBeta API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class OpenBetaController {

    private final ResponseService responseService;
    private final OpenBetaService openBetaService;
    private final ApplicantRepository applicantRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;

    @Operation(summary = "오픈베타 지원자 등록", description = "휴대폰 번호, 시, 구 번호로 등록")
    @PostMapping(value = "/applicants")
    public CommonResponse registerApplicant(@RequestBody @Valid ApplicantCreateReq dto, Errors errors) {
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        openBetaService.registerApplicant(dto);
        return responseService.getSuccessResponse();
    }

    @Operation(summary = "오픈베타 지원자 조회", description = "모든 지원자 조회")
    @GetMapping(value = "/applicants")
    public DataResponse<List<ApplicantListRes>> getAllApplicant() {
        List<ApplicantListRes> list = applicantRepository.getAllApplicant();
        return responseService.getDataResponse(list);
    }

    @Operation(summary = "시 조회", description = "모든 시 조회")
    @GetMapping(value = "/cities")
    public DataResponse<List<CityListRes>> getAllCity() {
        List<CityListRes> list = cityRepository.findAllByStatus(VALID);
        return responseService.getDataResponse(list);
    }

    @Operation(summary = "구 조회", description = "시 번호로 구 조회")
    @GetMapping(value = "/locations/{cityIndex}")
    public DataResponse<List<LocationListRes>> getAllLocationByCity(@PathVariable Integer cityIndex) {
        List<LocationListRes> list = openBetaService.getAllLocationByCity(cityIndex);
        return responseService.getDataResponse(list);
    }

}
