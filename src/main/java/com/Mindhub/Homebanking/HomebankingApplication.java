package com.Mindhub.Homebanking;

import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.Mindhub.Homebanking.models.TransactionType.CREDIT;
import static com.Mindhub.Homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (
			ClientRepository clientRepository ,
			AccountRepository accountRepository,
			TransactionRepository transactionRepository,
			LoanRepository loanRepository,
			ClientLoanRepository clientLoanRepository,
			CardRepository cardRepository) {
		return (args) -> {
			/*Clients*/
			Client clientOne = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123"));
			Client clientTwo = new Client("Frank", "Morel", "frank@mindhub.com" , passwordEncoder.encode("frank123"));
			Client adminOne = new Client("Silvina", "Silva", "silvina@admin.com", passwordEncoder.encode("admin123"));

			/*Assign accounts*/
			Account clientOneAccountOne = new Account("VIN001", LocalDateTime.now(), 5000.0, clientOne, true, AccountType.CHECKING);
			Account clientOneAccountTwo = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.0, clientOne,  true, AccountType.SAVING);
			Account clientTwoAccountOne = new Account("VIN003", LocalDateTime.now(), 9000.0, clientTwo, true, AccountType.SAVING);
			Account clientTwoAccountTwo = new Account("VIN004", LocalDateTime.now().plusDays(1), 3500.0, clientTwo, true, AccountType.CHECKING);

			/*Assign transactions*/
			Transaction transactionClientOneAccountOne = new Transaction(DEBIT, "salary", 2000.0, LocalDateTime.now(), clientOneAccountOne);
			Transaction transactionClientOneAccountTwo = new Transaction(CREDIT, "mortgage", 1000.0, LocalDateTime.now(), clientOneAccountTwo);
			Transaction transactionClientOneAccountTwoT = new Transaction(DEBIT, "food", 100.0, LocalDateTime.now(), clientOneAccountTwo);
			Transaction transactionClientTwoAccountOne = new Transaction(DEBIT, "salary", 3000.0, LocalDateTime.now(), clientTwoAccountOne);
			Transaction transactionClientTwoAccountTwo = new Transaction(CREDIT, "mortgage", 1500.0, LocalDateTime.now(), clientTwoAccountTwo);

			/*Assign loans*/
			List<Integer> paymentListOne = Arrays.asList(12, 24, 36, 48, 60);
			List<Integer> paymentListTwo = Arrays.asList(2,6,10, 12,14, 24);
			List<Integer> paymentListThree = Arrays.asList(6, 12, 24, 36,40);
			Loan loanOne = new Loan("Mortgage", 500000.0, paymentListOne, 0.25);
			Loan loanTwo = new Loan("Personal", 100000.0, paymentListTwo, 0.30);
			Loan loanThree = new Loan("Auto", 300000.0, paymentListThree, 0.20);

			/*Assign ClientLoans*/
			ClientLoan clientOneLoanOne = new ClientLoan(400000.0, 60, clientOne, loanOne);
			ClientLoan clientOneLoanTwo = new ClientLoan(50000.0, 12, clientOne, loanTwo);
			ClientLoan clientTwoLoanOne = new ClientLoan(100000.0, 24, clientTwo, loanTwo);
			ClientLoan clientTwoLoanTwo = new ClientLoan(200000.0, 36, clientTwo, loanThree);

			/*Assign ClientCards*/
			LocalDateTime startDate = LocalDateTime.now();
			LocalDateTime endDate = LocalDateTime.now().plusYears(5);
			LocalDateTime lastYear = LocalDateTime.now().minusYears(15);

			Card clientOneCardOne = new Card(clientOne.getName()+"  "+clientOne.getLastName(),CardType.DEBIT, CardColor.GOLD, "3325-6745-7876-4445", 990, startDate, endDate, clientOne, true);
			Card clientOneCardTwo = new Card(clientOne.getName()+"  "+clientOne.getLastName(),CardType.DEBIT, CardColor.TITANIUM, "4468-0943-6571-8821", 231, startDate, endDate, clientOne, true);
			Card clientOneCardThree = new Card(clientOne.getName()+"  "+clientOne.getLastName(),CardType.DEBIT, CardColor.SILVER, "3325-6745-7876-4445", 990, startDate, lastYear, clientOne, true);
			Card clientTwoCardOne = new Card(clientTwo.getName()+"  "+clientTwo.getLastName(), CardType.CREDIT, CardColor.SILVER, "9856-8821-3244-5587", 665, startDate, endDate, clientTwo, true);

		/*Save information (Clients)*/
		clientRepository.save(clientOne);
		clientRepository.save(clientTwo);
		clientRepository.save(adminOne);

		/*Save information (Accounts)*/
		accountRepository.save(clientOneAccountOne);
		accountRepository.save(clientOneAccountTwo);
		accountRepository.save(clientTwoAccountOne);
		accountRepository.save(clientTwoAccountTwo);

		/*Save information (Transaction)*/
			 transactionRepository.save(transactionClientOneAccountOne);
			 transactionRepository.save(transactionClientOneAccountTwo);
			 transactionRepository.save(transactionClientTwoAccountOne);
			 transactionRepository.save(transactionClientTwoAccountTwo);
			 transactionRepository.save(transactionClientOneAccountTwoT);

			/*Save information (Loan)*/
			loanRepository.save(loanOne);
			loanRepository.save(loanTwo);
			loanRepository.save(loanThree);

			/*Save information (ClientLoan)*/
			clientLoanRepository.save(clientOneLoanOne);
			clientLoanRepository.save(clientOneLoanTwo);
			clientLoanRepository.save(clientTwoLoanOne);
			clientLoanRepository.save(clientTwoLoanTwo);

			/*Save information (ClientCards)*/
			cardRepository.save(clientOneCardOne);
			cardRepository.save(clientOneCardTwo);
			cardRepository.save(clientTwoCardOne);
			cardRepository.save(clientOneCardThree);
		};
	}




}





