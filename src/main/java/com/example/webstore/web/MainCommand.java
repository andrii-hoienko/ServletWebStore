package com.example.webstore.web;

import com.example.webstore.model.Cart;
import com.example.webstore.model.Comparator.ProductComparatorByDate;
import com.example.webstore.model.Comparator.ProductComparatorByName;
import com.example.webstore.model.Comparator.ProductComparatorByNameReversed;
import com.example.webstore.model.Comparator.ProductComparatorByPrice;
import com.example.webstore.model.Product;
import com.example.webstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.webstore.controller.Path.PAGE_MAIN;
import static com.example.webstore.service.ProductService.getPages;
import static com.example.webstore.service.ProductService.getPagination;

public class MainCommand extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            Cart cart = new Cart();
            session.setAttribute("cart", cart);
        }
        ProductService productService = ProductService.getInstance();
        List<Product> productList = productService.getProducts();
        //filtering data
        String filterParameter = request.getParameter("filter");
        productService.productFilter(filterParameter, productList);
        //pages list
        int PAGE_SIZE = 4;
        request.setAttribute("pagesArray", getPages(productList, PAGE_SIZE));
        String page = request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        int currentPage = Integer.parseInt(page);
        getPagination(productList, currentPage, PAGE_SIZE);
        request.setAttribute("products", getPagination(productList, currentPage, PAGE_SIZE));
        return PAGE_MAIN;
    }
}