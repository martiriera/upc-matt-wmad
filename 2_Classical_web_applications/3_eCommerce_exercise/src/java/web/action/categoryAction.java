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
import entity.Category;

/**
 *
 * @author reir
 */
public class categoryAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;

    public categoryAction(CategoryModel categoryModel, ProductModel productModel) {
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("categoryId");
        Category category = categoryModel.retrieveCategoryById(Integer.parseInt(categoryId));
        req.getSession().setAttribute("lastCategory", category);
        
        req.setAttribute("categories", categoryModel.retrieveAll());
        req.setAttribute("products", productModel.retriveProductsByCategory(category));
        
        ViewManager.nextView(req, resp, "/view/category.jsp");
    }

}
