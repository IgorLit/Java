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
        //UserController user = (UserController) session.get(UserController.class,2);
      /*  String sql = "UPDATE cards set amount = :amount where id = :id";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(UserController.class);
        query.setParameter("amount",this.amount-amount);
        query.setParameter("id",this.id);
        int result = query.executeUpdate();*/

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
