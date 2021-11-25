package com.project.kcookserver.openBeta.entity;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Applicant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String phoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cityId")
    private City city;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

    public Applicant(String phoneNumber, City city, Location location) {
        this.status = VALID;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.location = location;
    }

}
