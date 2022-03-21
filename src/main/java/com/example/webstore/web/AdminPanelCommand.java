package com.example.webstore.web;

import com.example.webstore.model.Order;
import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.service.OrderService;
import com.example.webstore.service.ProductService;
import com.example.webstore.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.example.webstore.controller.Path.PAGE_ADMIN;
import static com.example.webstore.controller.Path.PAGE_MAIN;

public class AdminPanelCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(isAdmin(request)) {
            return PAGE_ADMIN;
        }
        return PAGE_MAIN;
    }
}
