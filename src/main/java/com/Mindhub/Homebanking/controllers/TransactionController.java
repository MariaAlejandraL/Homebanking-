package com.Mindhub.Homebanking.controllers;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.models.TransactionType;
import com.Mindhub.Homebanking.repositories.Services.AccountService;
import com.Mindhub.Homebanking.repositories.Services.ClientService;
import com.Mindhub.Homebanking.repositories.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

   @Autowired
    AccountService accountService;

   @Autowired
    ClientService clientService;

   @Autowired
    TransactionService transactionService;


    @Transactional
    @PostMapping(path = "/api/transactions")
    public ResponseEntity <Object> createTransaction  (Authentication authentication,
                                                       @RequestParam Double amount,
                                                       @RequestParam String description,
                                                       @RequestParam String originAccount,
                                                       @RequestParam String destinyAccount) {

        if (amount.toString().isEmpty() || description.isEmpty() || originAccount.isEmpty() || destinyAccount.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (originAccount.equals(destinyAccount)) {
            return new ResponseEntity<>("Equal Accounts", HttpStatus.FORBIDDEN);
        }
        if (accountService.findNumberAccount(originAccount) == null) {
            return new ResponseEntity<>("Source account does not exist", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findClientEmail(authentication.getName());
        Account originClientAccount = accountService.findNumberAccount(originAccount);
        Account destinyClientAccount = accountService.findNumberAccount(destinyAccount);
        List<Account>  authenticatedClientAccounts = client.getAccounts().stream().filter(account -> Objects.equals(account.getNumber(), originAccount)).collect(Collectors.toList());

        if (authenticatedClientAccounts.size() == 0){
            return new ResponseEntity<>("The source account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }


        if ( destinyClientAccount== null ) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }

        if(originClientAccount.getBalance() < amount ){
            return new ResponseEntity<>("There is not enough money for you transaction",HttpStatus.FORBIDDEN);
        }


        Transaction originDebitTransaction = new Transaction(TransactionType.DEBIT, description , -amount , LocalDateTime.now() , originClientAccount);
        Transaction destinyCreditTransaction = new Transaction(TransactionType.CREDIT, description, +amount, LocalDateTime.now(), destinyClientAccount);

        originClientAccount.setBalance(originClientAccount.getBalance()-amount);
        destinyClientAccount.setBalance(destinyClientAccount.getBalance()+amount);

        transactionService.saveTransaction(originDebitTransaction);
        transactionService.saveTransaction(destinyCreditTransaction);
        return new ResponseEntity<>("Successful transaction",HttpStatus.CREATED);

    }
}
