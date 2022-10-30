package com.Mindhub.Homebanking.DTO;

import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private TransactionType type;
    private String description;

    private Double amount;
    private LocalDateTime date;

    private Boolean activeTransactions;


    public TransactionDTO() {
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

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
    }
}
