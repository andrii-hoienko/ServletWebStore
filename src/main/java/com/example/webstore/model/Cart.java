package com.example.webstore.model;

import com.example.webstore.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

public class Cart {
    Map<Product, Integer> products;

    public String getFullPrice() {
        return String.format("%.2f", products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum());
    }

    public String getTotalAmount() {
        return Integer.toString(products.values().stream().mapToInt(i -> i).sum());
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Integer amount, Product product) {
        products.put(product, amount);
    }

    public void deleteByProduct(Product product) {
        products.remove(product);
    }

    public boolean isPresent(Product product) {
        return products.get(product) != null;
    }

    public List<Product> getProductList() {
        return new ArrayList<>(products.keySet());
    }

    public List<Integer> getAmountList() {
        return new ArrayList<>(products.values());
    }

    public void clear() {
        products.clear();
    }

}
