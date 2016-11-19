package main;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "User")
@SessionScoped
public class UserController {
    private String login;
    private Integer password;
    private boolean authorized=false;
    @ManagedProperty(value = "#{Card}")
    private CardController card;
    @ManagedProperty(value = "#{Recipient}")
    private RecipientCardController recipient;

    public String pay(){
        if(isAuthorized()){
            this.card = this.card.writeOff(recipient.getAmount());
            return "pay?faces-redirect=true";
        }
        return "false";
    }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }


    public String signIn(){
        if(this.login.equals("root")&& this.password==666) {
            this.initCard();
            this.setAuthorized(true);
            return "bank?faces-redirect=true";
        }
        else
            return "false";
    }
    private void initCard(){
        this.card.setAmount(1000);
        this.card.setCardExpiryDate("10.01.2017");
        this.card.setNumber(6662628);
    }
    public RecipientCardController getRecipient() {
        return recipient;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
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

    public CardController getCard() {
        return card;
    }

    public void setCard(CardController card) {
        this.card = card;
    }
}
