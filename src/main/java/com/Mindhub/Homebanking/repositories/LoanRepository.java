package com.Mindhub.Homebanking.repositories;


import com.Mindhub.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository <Loan, Long> {
    Loan findById (long id);

}
