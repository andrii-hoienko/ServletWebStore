package com.example.webstore.model;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


public class Product{
    private int id;
    private String name;
    private double price;
    private int totalAmount;
    private LocalDateTime date;
    private String color;

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(color, product.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, color);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", totalAmount=" + totalAmount +
                ", date=" + date +
                ", color='" + color + '\'' +
                '}';
    }
}
