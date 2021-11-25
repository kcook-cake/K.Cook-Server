package com.project.kcookserver.openBeta.repository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.openBeta.dto.ApplicantListRes;
import com.project.kcookserver.openBeta.entity.Applicant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByPhoneNumberAndStatus(String phoneNumber, Status status);

    @Query(
            "SELECT a FROM Applicant a " +
                    "LEFT JOIN FETCH a.city " +
                    "LEFT JOIN FETCH a.location " +
                    "WHERE a.status = 'VALID' "
    )
    List<ApplicantListRes> getAllApplicant();
}
