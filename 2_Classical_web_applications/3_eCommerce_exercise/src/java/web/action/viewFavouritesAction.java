/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import javax.servlet.http.*;
import web.ViewManager;
import model.CategoryModel;
import model.ProductModel;
import model.UserXProductModel;

/**
 *
 * @author reir
 */
public class viewFavouritesAction extends Action {
    
    UserXProductModel userXProductModel;

    public viewFavouritesAction(UserXProductModel userXProductModel) {
        this.userXProductModel = userXProductModel;
    }
    
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        req.getSession().setAttribute("favourites", userXProductModel.retrieveProductIdsByUserId(userId));
        ViewManager.nextView(req, resp, "/view/viewfavourites.jsp");
    }

}
