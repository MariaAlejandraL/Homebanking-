package com.Mindhub.Homebanking.DTO;


import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private String name;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> clientLoans;

    private Set<CardDTO>  clientCards;


    private long id;

    public ClientDTO() {
    }



    public ClientDTO(Client client ) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.clientLoans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.clientCards =client.getCard().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public Set<CardDTO> getClientCards() {
        return clientCards;
    }

    public void setClientCards(Set<CardDTO> clientCards) {
        this.clientCards = clientCards;
    }

    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoanDTO> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<AccountDTO> getAccount() {
        return accounts;
    }

    public void setAccount(Set<AccountDTO> account) {
        this.accounts = account;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }
}
