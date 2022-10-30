package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.DTO.AccountDTO;
import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repositories.Services.AccountService;
import com.Mindhub.Homebanking.repositories.Services.ClientService;
import com.Mindhub.Homebanking.repositories.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

     int min = 10000000;
     int max = 99999999;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String accountNumberString (){
        int accountNumber = getRandomNumber(min, max);
                return String.valueOf(accountNumber);
    }




    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccount() {
        return accountService.findAllAccount().stream().map(AccountDTO::new).collect(Collectors.toList());
    };

    @GetMapping("api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountService.findIdAccount(id));
    }

    @GetMapping("clients/current/accounts" )
    public List<AccountDTO> getAccountDto(Authentication authentication) {
        Client client = clientService.findClientEmail(authentication.getName());
        return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @PostMapping(path = "/api/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType) {

        Client client = clientService.findClientEmail(authentication.getName());
        List <AccountDTO> listAccounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        List <AccountDTO> activeAccounts =  listAccounts.stream().filter(accountDTO -> accountDTO.getActiveAccount()).collect(Collectors.toList());

         if(activeAccounts.size()>=3){
             return  new ResponseEntity<>("Account limit exceeded", HttpStatus.FORBIDDEN);
         }

        Account createAccount = new Account( "VIN"+ accountNumberString(), LocalDateTime.now(), 0.0 , client, true, accountType);
        accountService.saveAccount(createAccount);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/api/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id, Authentication authentication) {

        Account account = accountService.findIdAccount(id);
        account.setActiveAccount(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Deleted Account", HttpStatus.CREATED);


    }




}


