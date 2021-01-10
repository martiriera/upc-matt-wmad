/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author juanluis
 */
public class UserModel {

    UserTransaction utx;
    EntityManager em;

    public UserModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }

    public List<User> retrieveAll() {
        Query q = em.createQuery("select o from User as o");
        return q.getResultList();
    }

    public User retrieveUserById(Integer id) {
        Query q = em.createQuery("SELECT c FROM User c WHERE c.id = :userId")
                .setParameter("userId", id);
        User c = (User) q.getSingleResult();
        return c;
    }

    public void newUser(String username, String password) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        try {
            utx.begin();
            Query query = em.createNativeQuery("INSERT INTO user (username, password) VALUES ('" + username + "', '" + password + "')");
            query.executeUpdate();
        } catch (NotSupportedException e) {
            System.out.println("Exception" + e.getMessage());
        } catch (SystemException e) {
            System.out.println("Exception" + e.getMessage());
        }
        utx.commit();
    }

}
