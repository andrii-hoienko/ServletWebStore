package com.example.webstore.model.Comparator;

import com.example.webstore.model.Product;

import java.util.Comparator;

public class ProductComparatorByNameReversed implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return - o1.getName().compareTo(o2.getName());
    }
}
