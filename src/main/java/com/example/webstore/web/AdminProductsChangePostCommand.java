package com.example.webstore.web;

import com.example.webstore.model.Product;
import com.example.webstore.model.dto.ProductDTO;
import com.example.webstore.model.dto.Validator;
import com.example.webstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class AdminProductsChangePostCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAdmin(request)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductService productService = ProductService.getInstance();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(request.getParameter("name"));
            productDTO.setColor(request.getParameter("color"));
            productDTO.setPrice(Double.parseDouble(request.getParameter("price")));
            productDTO.setTotalAmount(Integer.parseInt(request.getParameter("totalAmount")));
            List<String> validationList = Validator.validate(productDTO);
            if (validationList.size() != 0) {
                request.setAttribute("validationErrors", validationList);
                return PAGE_ADMIN;
            }
            productService.update(productDTO, productId);
            return CommandContainer.get("admin-panel/products").execute(request, response);
        }
        return PAGE_ADMIN;
    }
}
