package com.example.webstore.web;

import com.example.webstore.model.Cart;
import com.example.webstore.model.Product;
import com.example.webstore.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import static com.example.webstore.controller.Path.PAGE_MAIN;

public class MainPostCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int orderedProductAmount = Integer.parseInt(request.getParameter("amount"));
        ProductService productService = ProductService.getInstance();
        Product product = productService.findById(productId);
        int availableProductAmount = product.getTotalAmount();
        if (availableProductAmount >= orderedProductAmount) {
            product.setTotalAmount(availableProductAmount - orderedProductAmount);
            productService.update(product);
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart.isPresent(product)) {
                Integer currentAmount = cart.getProducts().get(product);
                Integer newAmount = currentAmount + orderedProductAmount;
                cart.addProduct(newAmount, product);
            } else {
                cart.addProduct(orderedProductAmount, product);
            }
        }
        return CommandContainer.get("main").execute(request, response);
    }
}
