package com.Mindhub.Homebanking.repositories;

import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.CardColor;
import com.Mindhub.Homebanking.models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {


}
