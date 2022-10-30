package com.Mindhub.Homebanking.controllers;
import com.Mindhub.Homebanking.DTO.ClientDTO;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.AccountType;
import com.Mindhub.Homebanking.repositories.Services.AccountService;
import com.Mindhub.Homebanking.repositories.Services.ClientService;
import com.Mindhub.Homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

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



    @GetMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return  clientService.findAllClient().stream().map(ClientDTO::new).collect(Collectors.toList());
    };

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientService.findClientId(id));
    }


    @PostMapping(path = "/api/clients")
    public ResponseEntity<Object> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (name.isEmpty()) {
            return new ResponseEntity<>("Name is empty", HttpStatus.FORBIDDEN);
        }
        if ( lastName.isEmpty()) {
            return new ResponseEntity<>("Last Name is empty", HttpStatus.FORBIDDEN);
        }
        if ( email.isEmpty() ) {
            return new ResponseEntity<>("Email is empty", HttpStatus.FORBIDDEN);
        }
        if ( password.isEmpty()) {
            return new ResponseEntity<>("Password is empty", HttpStatus.FORBIDDEN);
        }



        if(!email.contains("@") || !email.contains(".com") ){
            return new ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        }
        if (clientService.findClientEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        if(password.length() < 8 ){
            return new ResponseEntity<>("The password must contain at least 8 characters", HttpStatus.FORBIDDEN);
        }

        Client client =  new Client(name, lastName, email, passwordEncoder.encode(password));
        Account newClientAccount = new Account( "VIN"+ accountNumberString(), LocalDateTime.now() , 0.0 , client , true , AccountType.SAVING);
        clientService.saveClient(client);
        accountService.saveAccount(newClientAccount);
            return new ResponseEntity<>( client,HttpStatus.CREATED);
    }


    @GetMapping("/api/clients/current")
    public ClientDTO getAll(Authentication authentication){
        return  new ClientDTO(clientService.findClientEmail(authentication.getName()));
    }

    @GetMapping("/api/clients/email")
    public  ClientDTO getEmails(String email){
        return  new ClientDTO(clientService.findClientEmail(email));
    }

}
