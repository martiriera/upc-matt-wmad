/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import entity.Product;
import cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

/**
 *
 * @author reir
 */
public class neworderAction extends Action {

    CategoryModel categoryModel;
    ProductModel productModel;
    Product product;
    ShoppingCart cart;

    public neworderAction(CategoryModel categoryModel, ProductModel productModel) {
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        this.cart = (ShoppingCart) session.getAttribute("sessionCart");
        if (this.cart == null) {
            this.cart = new ShoppingCart();
        }

        String productId = req.getParameter("productId");
        product = productModel.retrieveProductById(Integer.parseInt(productId));
        cart.add(product.getId(), product);
        session.setAttribute("sessionCart", cart);

        req.setAttribute("categoryId", product.getCategory().getId());
        req.setAttribute("category", product.getCategory());
        req.setAttribute("categories", categoryModel.retrieveAll());
        req.setAttribute("products", productModel.retriveProductsByCategory(product.getCategory()));
        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}
