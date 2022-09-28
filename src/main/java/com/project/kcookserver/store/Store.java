package com.project.kcookserver.store;

import static com.project.kcookserver.configure.entity.Status.VALID;
import static javax.persistence.FetchType.LAZY;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.dto.Coordinate;
import com.project.kcookserver.store.dto.CreateStoreReq;
import com.project.kcookserver.store.enums.Area;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    private String name;

    private String contact;

    private String address;

    @Enumerated(EnumType.STRING)
    private Area area;

    private Integer defaultPageStoreSequence;

    private double xCoordinate;
    private double yCoordinate;

    private String storeImage1;

    private String storeImage2;

    private String storeImage3;

    private String storeImage4;

    private String storeImage5;

    public Store(CreateStoreReq dto, Account account, Coordinate coordinate) {
        this.status = VALID;
        this.account = account;
        this.name = dto.getName();
        this.contact = dto.getContact();
        this.address = dto.getAddress();
        this.area = dto.getArea();
        this.xCoordinate = coordinate.getXCoordinate();
        this.yCoordinate = coordinate.getYCoordinate();
    }

    public void updateStore(CreateStoreReq dto) {
        this.name = dto.getName();
        this.contact = dto.getContact();
        this.address = dto.getAddress();
        this.area = dto.getArea();
    }

    public void changeDefaultPageSequence(int sequence) {
        this.defaultPageStoreSequence = sequence;
    }

    public void enrollCoordinate(Coordinate coordinate) {
        this.xCoordinate = coordinate.getXCoordinate();
        this.yCoordinate = coordinate.getYCoordinate();
    }

    public void setStoreImages(List<String> storeImages){
        if(!storeImages.get(0).isEmpty()) this.storeImage1 = storeImages.get(0);
        if(!storeImages.get(1).isEmpty()) this.storeImage2 = storeImages.get(1);
        if(!storeImages.get(2).isEmpty()) this.storeImage3 = storeImages.get(2);
        if(!storeImages.get(3).isEmpty()) this.storeImage4 = storeImages.get(3);
        if(!storeImages.get(4).isEmpty()) this.storeImage5 = storeImages.get(4);
    }
}
