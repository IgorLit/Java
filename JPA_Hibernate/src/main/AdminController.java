package main;



import main.Entities.DebitCard;
import main.utils.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 08.12.2016.
 */
@SessionScoped
@ManagedBean(name = "Admin")
public class AdminController {
    private List<UserController> userControllers = new LinkedList<UserController>();
    private List<DebitCard> debitCards = new LinkedList<DebitCard>();
    private Long changedCard=null;
    private String changedUser=null;
    public void changedCard(ValueChangeEvent e){
        this.changedCard = Long.parseLong(e.getNewValue().toString());
    }
    public void changedUser(ValueChangeEvent e){
        this.changedUser = e.getNewValue().toString();
    }

    public void getAllUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM users";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(UserController.class);
        this.userControllers =  query.list();
        session.close();
    }
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    public void appointCard(){
        if(changedUser!=null&&changedCard!=null){
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "SELECT * FROM users where login = :username";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(UserController.class);
            query.setParameter("username",this.changedUser);
            UserController user = (UserController) query.list().get(0);
            sql = "SELECT * FROM card where number = :number";
            query = session.createSQLQuery(sql);
            query.addEntity(DebitCard.class);
            query.setParameter("number",this.changedCard);
            DebitCard card = (DebitCard) query.list().get(0);
            Set<DebitCard> debitCards = new HashSet<DebitCard>();
            debitCards = user.getDebitCards();
            debitCards.add(card);
            user.setDebitCards(debitCards);
            session.update(user);
            session.getTransaction().commit();
            session.close();
        }
    }

    public List<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void setDebitCards(List<DebitCard> debitCards) {
        this.debitCards = debitCards;
    }

    public void getAddCards(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM card";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(DebitCard.class);
        this.debitCards =  query.list();

        session.close();
    }
    public List<UserController> getUserControllers() {
        return userControllers;
    }

    public void setUserControllers(List<UserController> userControllers) {
        this.userControllers = userControllers;
    }
}
