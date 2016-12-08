package main.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by User on 02.12.2016.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory buildSessionFactory() {
 /*
  * Hibernate Configuration Using Hibernate 4 Which uses the
  * StandardServiceRegistryBuilder Sample Configuration shown below
  * which is creating sessionFactory object from hibernate.cfg.xml
  */
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
