package com.Mindhub.Homebanking.repositories.Services.Implementations;
import com.Mindhub.Homebanking.repositories.Services.CardService;
import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CardServiceImp implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card){
         cardRepository.save(card);
    }

    @Override
    public Card getCardId(long id) { return cardRepository.findById(id).orElse(null); }
}
