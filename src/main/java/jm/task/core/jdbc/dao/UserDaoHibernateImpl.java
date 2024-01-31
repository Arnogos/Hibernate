package jm.task.core.jdbc.dao;

import com.sun.jdi.connect.spi.Connection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("""
                CREATE TABLE IF NOT EXISTS `sys`.`users` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL,
                  PRIMARY KEY (`id`));""");
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE users");
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }


    }
@Override
    public void saveUser(String name, String lastname, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastname, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        User user;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            user = session.getReference(User.class, id);
            if(user != null)
                session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();

        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User", User.class).list();
            userList.forEach(System.out::println);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        User user;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
                session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();

        }
    }
}
