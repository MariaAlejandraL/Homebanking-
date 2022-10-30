package com.Mindhub.Homebanking.repositories.Services.Implementations;

import com.Mindhub.Homebanking.repositories.Services.LoanService;
import com.Mindhub.Homebanking.models.Loan;
import com.Mindhub.Homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImp implements LoanService {


    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> findAllLoan(){
        return loanRepository.findAll();
    }

    @Override
    public Loan findIdLoan (long id){
        return loanRepository.findById(id);
    }

    @Override
    public void saveLoan(Loan loan){
        loanRepository.save(loan);
    }

}
