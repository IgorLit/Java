package main.Entities;

import main.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.metamodel.ValidationException;

import javax.inject.Named;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Named
@Debit
@Entity
@Table(name="CARD")
@DiscriminatorValue(value = "D")
public class DebitCard extends Card {
    public void writeOff(long amount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        long result =this.getAmount()-amount;
        if(result<0)
            throw new ValidationException("not enough money");
        else
            this.setAmount(result);
        session.update(this);
        session.getTransaction().commit();
        session.close();
    }

    public DebitCard() {
    }

    @Override
    public String toString() {
        return this.getNumber()+"";
    }
}
