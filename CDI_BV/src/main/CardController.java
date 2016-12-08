package main;

import main.utils.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;

@ManagedBean(name = "Card")
@SessionScoped
@Entity
@Table(name = "Cards")
public class CardController {
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
    private long amount;

    public int writeOff(long amount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        this.setAmount(this.amount-amount);
        session.update(this);
        session.getTransaction().commit();
        session.close();
        return 1;
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
