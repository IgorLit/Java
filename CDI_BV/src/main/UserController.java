package main;


import main.Adress.Address;
import main.Adress.AddressWithCountry;
import main.Annotations.Email;
import main.utils.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.annotations.Message;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@ManagedBean(name = "User")
@SessionScoped
@Entity
@Table(name = "Users")
public class UserController {

    private int id;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotNull(message="Please Enter Login")
    @Pattern(regexp = "[A-Z]*")
    private String login;
    @NotNull(message = "Please, Enter Password")
    private Integer password;
    @NotNull
    @Email
    private  String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManagedProperty(value = "#{Card}")
    private CardController card;

    public UserController() {
    }

    @ManagedProperty(value = "#{Recipient}")
    private RecipientCardController recipient;

    @Inject
    @AddressWithCountry
    private Address address;

    @OneToOne
    @JoinColumn(name = "addressId")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String pay(){

            this.card.writeOff(this.recipient.getAmount());
            return "pay?faces-redirect=true";


    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    public boolean registerUser(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(this.card);
        session.save(this.address);
        session.save(this.recipient);
        this.setCard(this.card);
        this.setAddress(this.address);
        session.save(this);
        session.getTransaction().commit();
        session.close();
       return true;
    }

    public String signUp(){
       if( this.registerUser())
        return "bank?faces-redirect=true";
        else
            return "index?faces-redirect=true";
    }
    public String signIn(){


        Session session = HibernateUtil.getSessionFactory().openSession();

        //UserController user = (UserController) session.get(UserController.class,2);
        String sql = "SELECT * FROM users where login = :username";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(UserController.class);
        query.setParameter("username",this.getLogin());
        UserController user = (UserController) query.list().get(0);
        session.close();
        if(user!=null && user.getPassword().equals(this.getPassword())){
            this.setAddress(user.getAddress());
            this.setCard(user.getCard());
            return "bank?faces-redirect=true";
        }
        else
            return "index?faces-redirect=true";
    }
    @Access(AccessType.PROPERTY)
    @OneToOne
    public RecipientCardController getRecipient() {
        return recipient;
    }


    public void setRecipient(RecipientCardController recipient) {
        this.recipient = recipient;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    @Access(AccessType.PROPERTY)
    @OneToOne
    @JoinColumn(name = "cardId")
    public CardController getCard() {
        return card;
    }

    public void setCard(CardController card) {
        this.card = card;
    }
}
