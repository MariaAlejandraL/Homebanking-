package com.Mindhub.Homebanking.controllers;
import com.Mindhub.Homebanking.DTO.AccountDTO;
import com.Mindhub.Homebanking.repositories.Services.CardService;
import com.Mindhub.Homebanking.repositories.Services.ClientService;
import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.CardColor;
import com.Mindhub.Homebanking.models.CardType;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;



    @PostMapping(path = "/api/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client clientCurrent = clientService.findClientEmail(authentication.getName());


        List<Card> listCardType = clientCurrent.getCard().stream().filter(card ->
                (card.getType() == cardType )).collect(Collectors.toList());
        List <Card> listTypeActive = listCardType.stream().filter(card -> card.getActiveCard()).collect(Collectors.toList());



        if (listTypeActive.size() >= 3 ) {
            return new ResponseEntity<>("No more cards can be created", HttpStatus.FORBIDDEN);
        }
        if(listTypeActive.stream().filter(card -> card.getColor().equals(cardColor)).collect(Collectors.toList()).size() > 0){
            return new ResponseEntity<>("No more cards of this color can be created", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber();

        int cvv = CardUtils.getCVV();

        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime thruDate = fromDate.plusYears(5);

        Card card = new Card(clientCurrent.getName() + " " + clientCurrent.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate, thruDate, clientCurrent, true);
        cardService.saveCard(card);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @PatchMapping("/api/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id, Authentication authentication){

        Card card = cardService.getCardId(id);
        card.setActiveCard(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("Deleted Card", HttpStatus.CREATED);
    }
}


