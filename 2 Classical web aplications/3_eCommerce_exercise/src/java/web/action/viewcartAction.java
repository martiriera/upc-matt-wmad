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

/**
 *
 * @author reir
 */
public class viewcartAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;

    public viewcartAction() {
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        ViewManager.nextView(req, resp, "/view/viewcart.jsp");
    }

}
