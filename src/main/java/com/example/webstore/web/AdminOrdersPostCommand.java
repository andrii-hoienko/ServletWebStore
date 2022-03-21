package com.example.webstore.web;

import com.example.webstore.model.Status;
import com.example.webstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.example.webstore.controller.Path.*;

public class AdminOrdersPostCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAdmin(request)) {
            String updatedStatus = request.getParameter("status");
            int orderId = Integer.parseInt(request.getParameter("orderToBeChanged"));
            OrderService orderService = OrderService.getInstance();
            orderService.changeStatus(updatedStatus, orderId);
            return CommandContainer.get("admin-panel/orders").execute(request, response);
        }
        return PAGE_ADMIN;
    }
}
