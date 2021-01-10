package cart;

import entity.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author juanluis
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reir
 */
public class ShoppingCart {

    HashMap<Integer, ShoppingCartItem> items = null;
    int numberOfItems = 0;

    public ShoppingCart() {
        items = new HashMap();
    }

    public void add(Integer productId, Product product) {
        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            items.put(productId, newItem);
        }
    }
    
    public void update(Integer productId, Integer newQuantity){
        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            scitem.setQuantity(newQuantity);

            if (scitem.getQuantity() <= 0) {
                items.remove(productId);
            }

            numberOfItems=this.getNumberOfItems();
        }
    }

    public List<ShoppingCartItem> getItems() {
        List<ShoppingCartItem> results = new ArrayList();
        results.addAll(this.items.values());

        return results;
    }

    public int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem item : getItems()) {
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public double getTotal() {
        double amount = 0.0;
        for (ShoppingCartItem item : getItems()) {
            Product product = (Product) item.getItem();
            amount += (item.getQuantity() * product.getPrice());
        }

        return roundOff(amount);
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); 

        return val / 100.0;
    }

    public String buy() {
        if (getNumberOfItems() < 1) {
            return (null);
        } else {
            return ("bookcashier");
        }
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
    }
}
