package com.Mindhub.Homebanking.repositories.Services;

import com.Mindhub.Homebanking.models.Client;

import java.util.List;

public interface ClientService {
    public List<Client> findAllClient();
    public  Client findClientId(long id);

    public  Client findClientEmail( String email);

    public void saveClient (Client client);

}
