package com.Mindhub.Homebanking.repositories.Services;

import com.Mindhub.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public List<Loan> findAllLoan ();

    public Loan findIdLoan (long id);


    public void saveLoan(Loan loan);
}
