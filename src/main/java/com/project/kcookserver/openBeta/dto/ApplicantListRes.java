package com.project.kcookserver.openBeta.dto;

import com.project.kcookserver.openBeta.entity.Applicant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantListRes {

    Long applicantId;
    String phoneNumber;
    String cityName;
    String locationName;

    public ApplicantListRes(Applicant applicant) {
        this.applicantId = applicant.getApplicantId();
        this.phoneNumber = applicant.getPhoneNumber();
        this.cityName = applicant.getCity().getName();
        if (applicant.getLocation() != null) {
            this.locationName = applicant.getLocation().getName();
        }
    }
}
