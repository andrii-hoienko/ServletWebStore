package com.example.webstore.web;

import com.example.webstore.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.webstore.controller.Path.PAGE_CART;

public class CartCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return PAGE_CART;
    }
}
