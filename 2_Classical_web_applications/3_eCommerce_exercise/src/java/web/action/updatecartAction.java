/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import javax.servlet.http.*;
import web.ViewManager;
import model.ProductModel;

/**
 *
 * @author reir
 */
public class updatecartAction extends Action {
    
    ProductModel productModel;
    ShoppingCart cart;
    
    public updatecartAction(ProductModel productModel) {
        this.productModel = productModel;
    }
    
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        this.cart = (ShoppingCart) req.getSession().getAttribute("sessionCart");
        Integer quantity = Integer.parseInt(req.getParameter("fquant"));
        Integer productId = Integer.parseInt(req.getParameter("fpid"));
        System.out.println(quantity);
        cart.update(productId, quantity);
        req.getSession().setAttribute("sessionCart", cart);
        ViewManager.nextView(req, resp, "/view/viewcart.jsp");
    }
    
}
