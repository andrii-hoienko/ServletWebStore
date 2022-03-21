package com.example.webstore.web;

import com.example.webstore.dao.ProductDAO;
import com.example.webstore.model.Cart;
import com.example.webstore.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CartDeleteCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productToDeleteId = Integer.parseInt(request.getParameter("productToDeleteId"));
        ProductDAO productDAO = ProductDAO.getInstance();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Product productToDelete = productDAO.findById(productToDeleteId).get();
        cart.deleteByProduct(productToDelete);
        return CommandContainer.get("cart").execute(request, response);
    }
}
