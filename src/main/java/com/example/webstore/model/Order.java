package com.example.webstore.model;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {
    private int id;
    private Map<Product, Integer> productMap;
    private Status status;
    private LocalDateTime date;
    private int userId;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    public List<Product> getProductList() {
        return new ArrayList<>(productMap.keySet());
    }

    public List<Integer> getProductAmountList() {
        return new ArrayList<>(productMap.values());
    }


    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productMap=" + productMap +
                ", status=" + status +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }
}