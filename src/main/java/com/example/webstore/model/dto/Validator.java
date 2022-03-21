package com.example.webstore.model.dto;

import com.example.webstore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Validator {

    public static Validator instance;
    private static final String EMAIL_REGEX = "[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}";

    public static synchronized Validator getInstance() {
        if (instance == null) {
            instance = new Validator();
        }
        return instance;
    }


    public static List<String>  validate(Object o) {
        if (o.getClass() == UserDTO.class) {
            return userValidation((UserDTO) o);
        } else if (o.getClass() == OrderDTO.class){
            return orderValidation((OrderDTO) o);
        } else if (o.getClass() == ProductDTO.class) {
            return productValidation((ProductDTO) o);
        }

        return new ArrayList<>();
    }

    private static List<String> userValidation(UserDTO userDTO) {
        List<String> list = new ArrayList<>();

        if (userDTO.getFirstName() == null ||
                userDTO.getFirstName().length() < 3 ||
                userDTO.getFirstName().length() > 30) {
            list.add("firstName");
        }

        if (userDTO.getLastName() == null ||
                userDTO.getLastName().length() < 3 ||
                userDTO.getLastName().length() > 30) {
            list.add("lastName");
        }

        if (userDTO.getEmail() == null ||
                !userDTO.getEmail().matches(EMAIL_REGEX)) {
            list.add("email");
        }

        if (userDTO.getPassword() == null ||
                userDTO.getPassword().length() < 3 ||
                userDTO.getPassword().length() > 30) {
            list.add("password");
        }

        return list;
    }


    private static List<String> productValidation(ProductDTO productDTO) {
        List<String> list = new ArrayList<>();

        String productName = productDTO.getName();
        if (productName == null ||
                productName.length() < 1 ||
                productName.length() > 30) {
            list.add("name");
        }

        String productColor = productDTO.getColor();
        if (productColor == null ||
                productColor.length() < 1 ||
                productColor.length() > 30) {
            list.add("color");
        }

        if (productDTO.getPrice() < 0) {
            list.add("price");
        }

        if (productDTO.getTotalAmount() < 0) {
            list.add("total amount");
        }

        return list;
    }

    private static List<String> orderValidation(OrderDTO orderDTO) {
        List<String> list = new ArrayList<>();
        List<Integer> productAmount = orderDTO.getProductAmount();
        List<Product> products = orderDTO.getProductList();

        if (productAmount == null) {
            for(Integer amount: productAmount) {
                if (amount < 1) {
                    list.add("Amount");
                }
            }
        }

        if (products == null) {
            list.add("Products");
        }

        return list;
    }
}
