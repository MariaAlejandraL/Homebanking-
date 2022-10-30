package com.Mindhub.Homebanking.controllers;
import com.Mindhub.Homebanking.DTO.LoanApplicationDTO;
import com.Mindhub.Homebanking.DTO.LoanDTO;
import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repositories.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    AccountService accountService;


    @Autowired
    ClientService clientService;


    @Autowired
    LoanService loanService;

    @Autowired
    TransactionService transactionService;





    @GetMapping(path = "/api/loans")
   public List<LoanDTO> getLoans() {
    return  loanService.findAllLoan().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    };



   @Transactional
   @PostMapping(path = "/api/loans")
  public ResponseEntity<String> addLoan (@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

       if(String.valueOf(loanApplicationDTO.getAmount()).isEmpty() || String.valueOf(loanApplicationDTO.getPayments()).isEmpty()|| loanApplicationDTO.getDestinyAccount().isEmpty() ) {
           return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
       }

       if(loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0){
           return new ResponseEntity<>("Values can not be zero", HttpStatus.FORBIDDEN);
       }

       Loan loanId = loanService.findIdLoan(loanApplicationDTO.getId());

       if(loanId == null){
           return new ResponseEntity<>("Loan doesn't exist", HttpStatus.FORBIDDEN);
       }

       if(loanId.getMaxAmount() < loanApplicationDTO.getAmount()){
           return new ResponseEntity<>("The amount is not available", HttpStatus.FORBIDDEN);
       }


       if(!loanId.getPayments().contains(loanApplicationDTO.getPayments())){
           return new ResponseEntity<>("Payments are not available", HttpStatus.FORBIDDEN);
       }

       Account destinyAccount = accountService.findNumberAccount(loanApplicationDTO.getDestinyAccount());

       if(destinyAccount ==null){
           return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
       }

       Client client = clientService.findClientEmail(authentication.getName());
       List<Account>  authenticatedClientAccount = client.getAccounts().stream().filter(account -> Objects.equals(account.getNumber(), loanApplicationDTO.getDestinyAccount())).collect(Collectors.toList());

       if(authenticatedClientAccount.size()==0){
           return new ResponseEntity<>("The destiny account doesn't belong to the authenticated client", HttpStatus.FORBIDDEN);
       }



       ClientLoan newClientLoan = new ClientLoan((+loanApplicationDTO.getAmount()*loanId.getPercentage())+ loanApplicationDTO.getAmount() , loanApplicationDTO.getPayments(), client, loanId);
       Transaction loanTransaction = new Transaction(TransactionType.CREDIT, loanId.getName()+ " " + "Loan approved", loanApplicationDTO.getAmount() , LocalDateTime.now(), destinyAccount );

       destinyAccount.setBalance(destinyAccount.getBalance()+ loanApplicationDTO.getAmount());

       transactionService.saveTransaction(loanTransaction);
       clientLoanService.saveClientLoan(newClientLoan);

       return new ResponseEntity<>("Loan application approved",HttpStatus.CREATED);
   }

    @Transactional
    @PostMapping(path = "/api/loans/admin")
    public ResponseEntity<String> createLoan (@RequestParam String name, @RequestParam Double maxAmount, @RequestParam List <Integer> payments, @RequestParam Double percentage , Authentication authentication){




        if(name.isEmpty()){
            return new ResponseEntity<>("Name is empty", HttpStatus.FORBIDDEN);
        }
        if(payments.isEmpty()){
            return new ResponseEntity<>("Payments is empty", HttpStatus.FORBIDDEN);
        }
        if(maxAmount.toString().isEmpty()){
            return new ResponseEntity<>("Amount is empty", HttpStatus.FORBIDDEN);
        }

        if(percentage.toString().isEmpty()){
            return new ResponseEntity<>("Percentage is empty", HttpStatus.FORBIDDEN);
        }

        if(maxAmount <= 0 ){
            return new ResponseEntity<>("Values can not be zero", HttpStatus.FORBIDDEN);
        }

        if(percentage<= 0){
            return new ResponseEntity<>("Values can not be zero", HttpStatus.FORBIDDEN);
        }


        Client admin = clientService.findClientEmail(authentication.getName());

        Loan newLoanAdmin =  new Loan( name , maxAmount , payments , percentage );

        loanService.saveLoan(newLoanAdmin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
