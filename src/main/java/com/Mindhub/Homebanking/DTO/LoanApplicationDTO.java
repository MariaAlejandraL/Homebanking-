package com.Mindhub.Homebanking.DTO;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Loan;
import java.util.List;
import java.util.Set;

public class LoanApplicationDTO {

    private long id;
    private double amount;
    private int payments;
    private String destinyAccount;



    public long getId() {
        return id;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(String destinyAccount) {
        this.destinyAccount = destinyAccount;
    }
}




