package com.project.kcookserver.store;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.dto.CreateStoreReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

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

    private String area;

    public Store(CreateStoreReq dto, Account account) {
        this.status = VALID;
        this.account = account;
        this.name = dto.getName();
        this.contact = dto.getContact();
        this.address = dto.getAddress();
        this.area = dto.getArea();
    }

    public void updateStore(CreateStoreReq dto) {
        this.name = dto.getName();
        this.contact = dto.getContact();
        this.address = dto.getAddress();
        this.area = dto.getArea();
    }

}
