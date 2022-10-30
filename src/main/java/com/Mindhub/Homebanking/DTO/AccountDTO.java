package com.Mindhub.Homebanking.DTO;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.AccountType;
import com.Mindhub.Homebanking.models.Client;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Set<TransactionDTO> transactions;

    private Boolean activeAccount;

    private AccountType typeAccount;

    public AccountDTO() {
    }

    public AccountDTO (Account account){
        this.id  = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.activeAccount = account.getActiveAccount();
        this.typeAccount = account.getTypeAccount();
    }



    public AccountDTO(Client client) {
    }

    public AccountType getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(AccountType typeAccount) {
        this.typeAccount = typeAccount;
    }

    public Boolean getActiveAccount() {
        return activeAccount;
    }



    public void setActiveAccount(Boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }


    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }
}
