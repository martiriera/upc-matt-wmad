/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Product;
import entity.User;
import entity.UserXProduct;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
public class UserXProductModel {

    UserTransaction utx;
    EntityManager em;

    public UserXProductModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }

    public List<Product> retrieveProductIdsByUserId(Integer id) {
        TypedQuery<Product> query
                = em.createQuery("SELECT p FROM Product p INNER JOIN UserXProduct uxp ON uxp.productid=p.id where uxp.userid = :userId ", Product.class)
                .setParameter("userId", id);
        List<Product> results = query.getResultList();
        return results;

    }

    public void insertFavourite(Integer userId, Integer productId) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        String userIdString = String.valueOf(userId);
        String productIdString = String.valueOf(productId);

        try {
            utx.begin();
            Query query = em.createNativeQuery("INSERT INTO user_product (userId, productId) VALUES (" + userIdString + ", " + productIdString + ")");
            query.executeUpdate();
        } catch (NotSupportedException e) {
            System.out.println("Exception" + e.getMessage());
        } catch (SystemException e) {
            System.out.println("Exception" + e.getMessage());
        }
        utx.commit();
    }

    public void removeFavourite(Integer userId, Integer productId) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        String userIdString = String.valueOf(userId);
        String productIdString = String.valueOf(productId);
        try {
            utx.begin();
            Query query = em.createNativeQuery("DELETE FROM user_product WHERE user_product.userId=" + userIdString + " AND " + "user_product.productId=" + productIdString);
            query.executeUpdate();
        } catch (NotSupportedException e) {
            System.out.println("Exception" + e.getMessage());
        } catch (SystemException e) {
            System.out.println("Exception" + e.getMessage());
        }
        utx.commit();
    }

}
