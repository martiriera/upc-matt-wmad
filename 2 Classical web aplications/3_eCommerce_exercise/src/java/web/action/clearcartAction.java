/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import javax.servlet.http.*;
import web.ViewManager;

/**
 *
 * @author reir
 */
public class clearcartAction extends Action {

    ShoppingCart cart;

    public clearcartAction() {
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        this.cart = (ShoppingCart) session.getAttribute("sessionCart");
        cart.clear();
        ViewManager.nextView(req, resp, "/view/viewcart.jsp");
    }

}
