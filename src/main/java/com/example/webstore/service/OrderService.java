package com.example.webstore.service;

import com.example.webstore.dao.OrderDAO;
import com.example.webstore.model.Order;
import com.example.webstore.model.Product;
import com.example.webstore.model.Status;
import com.example.webstore.model.User;
import com.example.webstore.model.dto.OrderDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderService {

    private final OrderDAO orderDAO;
    private static OrderService instance;

    public static synchronized OrderService getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderService(OrderDAO.getInstance());
        }
        return instance;
    }

    private OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> getOrders() {
        return orderDAO.findAll();
    }

    public Order createOrder(OrderDTO orderDTO) {
        int userId = orderDTO.getUser().getId();
        Order orderToCreate = new Order();
        orderToCreate.setDate(LocalDateTime.now());
        orderToCreate.setStatus(Status.REGISTRATIONS);
        orderToCreate.setProductMap(orderDTO.getProducts());
        orderToCreate.setUserId(userId);
        return orderDAO.create(orderToCreate);
    }

    public void changeStatus(String status, int orderId) throws Exception {
        Optional<Order> orderToChange = orderDAO.findById(orderId);
        if (orderToChange.isPresent()) {
            Order order = orderToChange.get();
            order.setStatus(Status.valueOf(status));
            orderDAO.update(order);
        }
    }

    public List<Order> getOrdersForUser(User user) {
        List<Order> orderList = orderDAO.findOrdersByUserId(user.getId());
        return orderList;
    }
}
