package main.Entities;

import javax.inject.Named;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by User on 06.12.2016.
 */
@Named
@Credit
@Entity
@Table(name="CARD")
@DiscriminatorValue(value = "CR")
public class CreditCard extends Card {
    public CreditCard() {
    }
    @Override
    public String toString() {
        return this.getNumber()+"";
    }
}
