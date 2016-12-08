package main.Entities;

import javax.enterprise.inject.Produces;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 06.12.2016.
 */
public class CardProducer {

    @Produces @BasicCard Set<Card> getBasic() {
        return new HashSet<Card>();
    }

    @Produces @Debit
    HashSet<DebitCard> getDebit() {
        return new HashSet<DebitCard>();
    }

    @Produces @Credit Set<CreditCard> getCredit() {
        return new HashSet<CreditCard>();
    }
}
