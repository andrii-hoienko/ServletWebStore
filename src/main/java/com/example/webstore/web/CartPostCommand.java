package com.example.webstore.web;

import com.example.webstore.model.Cart;
import com.example.webstore.model.Order;
import com.example.webstore.model.User;
import com.example.webstore.model.dto.OrderDTO;
import com.example.webstore.model.dto.Validator;
import com.example.webstore.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.webstore.controller.Path.PAGE_CART;

public class CartPostCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isUser(request) || isAdmin(request)) {
            OrderService orderService = OrderService.getInstance();
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            User user = (User) request.getSession().getAttribute("user");
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setProducts(cart.getProducts());
            orderDTO.setUser(user);
            if (Validator.validate(orderDTO).size() != 0) {
                return PAGE_CART;
            }
            orderService.createOrder(orderDTO);
            cart.clear();
            request.setAttribute("isOrderDone", true);
        } else {
            request.setAttribute("accessDenied", true);
        }
        return PAGE_CART;
    }
}
