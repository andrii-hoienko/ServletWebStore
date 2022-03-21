package com.example.webstore.model.dto;

import com.example.webstore.model.Product;
import com.example.webstore.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDTO {
    private User user;
    private Map<Product, Integer> products;

    public List<Product> getProductList() {
        return new ArrayList<>(products.keySet());
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public List<Integer> getProductAmount() {
        return new ArrayList<>(products.values());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
}
