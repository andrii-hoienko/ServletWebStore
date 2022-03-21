package com.example.webstore.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.example.webstore.controller.Path.*;

public class AdminProductsChangeCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        if (isAdmin(request)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            request.setAttribute("productId", productId);
            return PAGE_ADMIN_PRODUCTS_CHANGE;
        }
        return PAGE_MAIN;
    }
}
