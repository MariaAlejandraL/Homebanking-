package com.Mindhub.Homebanking;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import com.Mindhub.Homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CardUtilsTests {


    @Test

    public void cardNumberIsCreated(){

        String cardNumber = CardUtils.getCardNumber();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }

    @Test

    public void cardCVVIsCreated(){

        String cardCVV = String.valueOf(CardUtils.getCVV());

        assertThat(cardCVV,is(not(emptyOrNullString())));

    }



}
