package com.example.webstore.web;

import com.example.webstore.model.Order;
import com.example.webstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class AdminOrdersCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        if (isAdmin(request)) {
            OrderService orderService = OrderService.getInstance();
            List<Order> orderList = orderService.getOrders();
            request.setAttribute("orders", orderList);
            return PAGE_ADMIN_ORDERS;
        }
        return PAGE_ADMIN;
    }
}
