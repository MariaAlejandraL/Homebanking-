package com.Mindhub.Homebanking.repositories.Services;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.models.Transaction;

import java.util.List;
import java.util.Set;

public interface AccountService {
    public List<Account> findAllAccount();

    public Account findNumberAccount(String number);

    public Account findIdAccount(long id);

    public void saveAccount (Account account);




}
