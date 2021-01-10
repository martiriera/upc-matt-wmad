/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import javax.servlet.http.*;
import web.ViewManager;

/**
 *
 * @author reir
 */
public class checkoutAction extends Action {
    public checkoutAction() {
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String amount = req.getParameter("amountToPay");
        req.setAttribute("amountToPay", amount);
        ViewManager.nextView(req, resp, "/view/checkout.jsp");
    }

}
