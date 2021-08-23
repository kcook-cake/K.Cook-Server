package com.project.kcookserver.store;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


}
