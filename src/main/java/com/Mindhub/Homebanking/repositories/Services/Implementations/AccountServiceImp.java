package com.Mindhub.Homebanking.repositories.Services.Implementations;


import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.repositories.Services.AccountService;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public  Account findNumberAccount(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public List<Account> findAllAccount(){
        return accountRepository.findAll();
    }

    @Override
    public Account findIdAccount(long id){
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount (Account account){
        accountRepository.save(account);
    }



}


