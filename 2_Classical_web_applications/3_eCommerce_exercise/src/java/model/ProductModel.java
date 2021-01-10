/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Category;
import entity.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author juanluis
 */
public class ProductModel {

    UserTransaction utx;
    EntityManager em;

    public ProductModel(EntityManager em, UserTransaction utx) {
        this.utx = utx;
        this.em = em;
    }

    public List<Category> retrieveAll() {
        Query q = em.createQuery("select o from Category as o");
        return q.getResultList();
    }
    
    public Product retrieveProductById(Integer id) {
        Query q = em.createQuery(
                "SELECT p FROM Product p WHERE p.id = :productId")
                .setParameter("productId", id);
        Product p = (Product) q.getSingleResult();
        return p;
    }

    public List<Product> retriveProductsByCategory(Category category) {
        Query q = em.createQuery(
                "SELECT p FROM Product p WHERE p.category = :category")
                .setParameter("category", category);
        return q.getResultList();
    }

}
