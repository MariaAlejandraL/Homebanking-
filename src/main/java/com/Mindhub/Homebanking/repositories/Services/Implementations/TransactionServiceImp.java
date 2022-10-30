package com.Mindhub.Homebanking.repositories.Services.Implementations;

import com.Mindhub.Homebanking.repositories.Services.TransactionService;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction (Transaction transaction){
        transactionRepository.save(transaction);
    }
}
