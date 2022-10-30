package com.Mindhub.Homebanking.repositories.Services;

import com.Mindhub.Homebanking.models.ClientLoan;
import com.Mindhub.Homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface ClientLoanService {
    public void saveClientLoan(ClientLoan clientLoan);

}
