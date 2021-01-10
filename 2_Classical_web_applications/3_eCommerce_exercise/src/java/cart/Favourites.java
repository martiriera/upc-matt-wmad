/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;
import java.util.HashMap;

/**
 *
 * @author reir
 */
public class Favourites {

    HashMap<Integer, Product> favs = null;
    int numberOfItems = 0;

    public Favourites() {
        favs = new HashMap();
    }

    public void add(Integer productId, Product product) {
        favs.put(productId, product);
    }
    
    public void remove(Integer productId, Product product) {
        favs.remove(productId, product);
    }

}
