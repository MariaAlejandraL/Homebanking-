package com.Mindhub.Homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType type;
    private String description;
    private Double amount;
    private LocalDateTime date;

    private Boolean activeTransactions=true;






    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction (){}

    public Transaction(TransactionType type, String description, Double amount, LocalDateTime date, Account account) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.account = account;
    }


    public Boolean getActiveTransactions() {
        return activeTransactions;
    }

    public void setActiveTransactions(Boolean activeTransactions) {
        this.activeTransactions = activeTransactions;
    }

    public long getId() {
        return id;
    }


    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
