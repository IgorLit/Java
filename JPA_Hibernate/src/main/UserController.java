package main;


import main.Adress.Address;
import main.Adress.AddressWithCountry;
import main.Annotations.Email;
import main.Entities.*;
import main.utils.HibernateUtil;

import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.annotations.Message;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ManagedBean(name = "User")
@SessionScoped
@Entity
@Table(name = "Users")
public class UserController {

    private int id;

    private Long changedCard=null;
    public void changedCard(ValueChangeEvent e){
        this.changedCard = Long.parseLong(e.getNewValue().toString());
    }
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotNull(message="Please Enter Login")
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


    @Inject
    @Debit
    private Set<DebitCard> debitCards;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "ACCOUNT",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CARD_ID") })
    public Set<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void setDebitCards(Set<DebitCard> debitCards) {
        this.debitCards = debitCards;
    }
    @Inject
    @Credit
    private  Set<CreditCard> creditCards;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "ACCOUNT",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CARD_ID") })
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public String pay(){
        if(changedCard!=null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "SELECT * FROM card where number = :number";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(DebitCard.class);
            query.setParameter("number", this.changedCard);
            DebitCard card = (DebitCard) query.list().get(0);
            card.writeOff(this.recipient.getAmount());
            sql = "SELECT * FROM users where login = :login";
             query = session.createSQLQuery(sql);
            query.addEntity(UserController.class);
            query.setParameter("login", this.getLogin());
            UserController userController = (UserController) query.list().get(0);
            this.setDebitCards(userController.getDebitCards());
            session.close();
            return "pay?faces-redirect=true";
        }
        return "false";

    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    public boolean registerUser(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
       // session.save(this.card);
        session.save(this.address);
        session.save(this.recipient);
        //this.setCard(this.card);
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

        if(this.getLogin().equals("admin")&&this.getPassword()==666){
            return "admin?faces-redirect=true";
        }
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
            this.setEmail(user.email);
            this.setDebitCards(user.getDebitCards());
            this.setCreditCards(user.getCreditCards());

           // this.setCard(user.getCard());
            return "bank?faces-redirect=true";
        }
        else
            return "index?faces-redirect=true";
    }
    public void addAccounts(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        DebitCard debitCard = new DebitCard();
        debitCard.setAmount(100000);
        debitCard.setCardExpiryDate("2017-11-11");
        debitCard.setNumber(1987654);

        session.save(debitCard);
     ;
        session.getTransaction().commit();
        session.close();
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

    @Override
    public String toString() {
        return this.getLogin();
    }
}
