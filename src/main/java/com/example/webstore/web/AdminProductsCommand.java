package com.example.webstore.web;

import com.example.webstore.model.Product;
import com.example.webstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class AdminProductsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        if (isAdmin(request)) {
            ProductService productService = ProductService.getInstance();
            List<Product> productList = productService.getProducts();
            request.setAttribute("products", productList);

            return PAGE_ADMIN_PRODUCTS;
        }
        return PAGE_MAIN;
    }
}
