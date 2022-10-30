package com.Mindhub.Homebanking.repositories.Services.Implementations;


import com.Mindhub.Homebanking.repositories.Services.ClientLoanService;
import com.Mindhub.Homebanking.models.ClientLoan;
import com.Mindhub.Homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImp implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    public void saveClientLoan (ClientLoan clientLoan){
        clientLoanRepository.save(clientLoan);
    }
}
