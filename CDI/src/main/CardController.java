package main;

import javax.faces.bean.*;

@ManagedBean(name = "Card")
@SessionScoped
public class CardController {
    private long number;
    private String cardExpiryDate;
    private long amount;

    public CardController writeOff(long amount){
        //TODO sync with DB
        setAmount(this.amount-amount);
        return this;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
