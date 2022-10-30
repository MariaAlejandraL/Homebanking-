package com.Mindhub.Homebanking;

import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

//los test a los repositorios se realizaron verificaciones automáticas
// para saber si la comunicación es correcta entre la base de datos y la aplicación
// así como para determinar que el gestor de base de datos estaba retornando los resultados correctos

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    AccountRepository accountRepository;
    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existNumberAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));
    }


    @Autowired
    CardRepository cardRepository;
    @Test
    public void existCards(){
        List <Card> card =  cardRepository.findAll();
        assertThat(card,is(not(empty())));
    }
    @Test
    public void existCardNumber(){
        List <Card> card =  cardRepository.findAll();
        assertThat(card, hasItem(hasProperty("number", is("3325-6745-7876-4445"))));
    }


    @Autowired
    ClientRepository clientRepository;
    @Test
    public void existClients(){
        List <Client> clients =  clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existClient(){
        List <Client> clients =  clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("name", is("Melba"))));
    }


    @Autowired
    LoanRepository loanRepository;
    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existTypeLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Autowired
    TransactionRepository transactionRepository;
    @Test
    public void existTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));

    }
    @Test
    public void existAmount(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("amount", is(2000.0))));
    }



}
