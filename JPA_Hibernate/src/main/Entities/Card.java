package main.Entities;


import main.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.annotations.BatchSize;

import javax.enterprise.inject.Default;
import javax.persistence.*;
@BasicCard
@Entity
@Table(name="CARD")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
        (
                name="Discriminator",
                discriminatorType=DiscriminatorType.STRING
        )
@DiscriminatorValue(value="C")
public class Card {
    private int id;
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private long number;
    private String cardExpiryDate;

    public Card() {
    }
    public void writeOff(long amount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        this.setAmount(this.amount-amount);
        session.update(this);
        session.getTransaction().commit();
        session.close();
    }


    private long amount;

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
