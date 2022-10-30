package com.Mindhub.Homebanking.DTO;

import com.Mindhub.Homebanking.models.ClientLoan;
import com.Mindhub.Homebanking.models.Loan;

public class ClientLoanDTO {

    private long id;
    private long LoanId;
    private String name;
    private Double amount;
    private  int payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoans) {
        this.id = clientLoans.getId();
        this.LoanId = clientLoans.getLoan().getId();
        this.name = clientLoans.getLoan().getName();
        this.amount = clientLoans.getMaxAmount();
        this.payments = clientLoans.getPayments();

    }

    public ClientLoanDTO(Loan loan) {
    }

    public long getId() {
        return id;
    }


    public long getLoanId() {
        return LoanId;
    }

    public void setLoanId(long loanId) {
        LoanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
}
