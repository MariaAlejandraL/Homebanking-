package com.Mindhub.Homebanking.repositories.Services;

import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.Client;

public interface CardService {


    public Card  getCardId (long id);

    public void saveCard (Card card);
}
