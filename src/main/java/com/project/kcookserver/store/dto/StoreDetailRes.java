package com.project.kcookserver.store.dto;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.entity.Store;
import com.project.kcookserver.store.enums.Area;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreDetailRes {

    private Long storeId;

    private Status status;

    private String accountName;

    private String name;

    private String contact;

    private String address;

    private Area area;

    private Double xCoordinate;

    private Double yCoordinate;

    private Integer defaultPageStoreSequence;

    public StoreDetailRes(Store store) {
        this.storeId = store.getStoreId();
        this.status = store.getStatus();
        this.accountName = store.getAccount().getNickname();
        this.name = store.getName();
        this.contact = store.getContact();
        this.address = store.getAddress();
        this.area = store.getArea();
        this.xCoordinate = store.getXCoordinate();
        this.yCoordinate = store.getYCoordinate();
        this.defaultPageStoreSequence = store.getDefaultPageStoreSequence();
    }

}
