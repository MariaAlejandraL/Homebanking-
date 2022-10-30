package com.Mindhub.Homebanking.repositories;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber (String number);


}
