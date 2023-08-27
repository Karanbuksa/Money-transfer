package com.example.moneytransferproj.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Account {
    private String cardNumber;
    private String cardDate;
    private String cvv;
    private Double balance;

    public void withdraw(Double sum) {
        balance -= sum;
    }

    public void deposit(Double sum) {
        balance += sum;
    }


}
