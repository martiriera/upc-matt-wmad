/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import model.CategoryModel;
import model.ProductModel;
import model.UserXProductModel;
import web.ViewManager;

/**
 *
 * @author reir
 */
public class removefavouriteAction extends Action {
    
    CategoryModel categoryModel;
    ProductModel productModel;
    UserXProductModel userXProductModel;
    List<Product> favourites;
    Product product;

    public removefavouriteAction(CategoryModel categoryModel, ProductModel productModel, UserXProductModel userXProductModel) {
        this.categoryModel = categoryModel;
        this.productModel = productModel;
        this.userXProductModel = userXProductModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        HttpSession session = req.getSession();

        this.favourites = (List<Product>) session.getAttribute("favourites");
        String productId = req.getParameter("productId");
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        product = productModel.retrieveProductById(Integer.parseInt(productId));
        
        favourites.remove(product);
        try {
            userXProductModel.removeFavourite(userId, Integer.parseInt(productId));
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        session.setAttribute("favourites", favourites);

        req.setAttribute("categoryId", product.getCategory().getId());
        req.setAttribute("category", product.getCategory());
        req.setAttribute("categories", categoryModel.retrieveAll());
        req.setAttribute("products", productModel.retriveProductsByCategory(product.getCategory()));
        ViewManager.nextView(req, resp, "/view/viewfavourites.jsp");
    }
}
