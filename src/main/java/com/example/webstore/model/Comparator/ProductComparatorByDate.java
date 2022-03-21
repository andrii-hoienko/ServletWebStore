package com.example.webstore.model.Comparator;

import com.example.webstore.model.Product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;

public class ProductComparatorByDate implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
