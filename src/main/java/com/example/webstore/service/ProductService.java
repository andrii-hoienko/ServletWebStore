package com.example.webstore.service;

import com.example.webstore.dao.ProductDAO;
import com.example.webstore.model.Comparator.ProductComparatorByDate;
import com.example.webstore.model.Comparator.ProductComparatorByName;
import com.example.webstore.model.Comparator.ProductComparatorByNameReversed;
import com.example.webstore.model.Comparator.ProductComparatorByPrice;
import com.example.webstore.model.Product;
import com.example.webstore.model.dto.ProductDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;
    private static ProductService instance;

    public static synchronized ProductService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductService(ProductDAO.getInstance());
        }
        return instance;
    }

    private ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void create(Product product) { productDAO.create(product); }

    public void delete(int productId) {
        productDAO.deleteById(productId);
    }

    public void update(ProductDTO productDTO, int id) throws Exception {
        Product productToUpdate = productDAO.findById(id).get();
        productToUpdate.setTotalAmount(productDTO.getTotalAmount());
        productToUpdate.setPrice(productDTO.getPrice());
        productToUpdate.setName(productDTO.getName());
        productToUpdate.setColor(productDTO.getColor());
        productToUpdate.setDate(LocalDateTime.now());
        productDAO.update(productToUpdate);
    }

    public void update(Product product) {
        productDAO.update(product);
    }

    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    public Product findById(int id) throws Exception {
        return productDAO.findById(id).get();
    }
    public void productFilter(String filterParameter, List<Product> productList) {
        if (filterParameter != null) {
            if (filterParameter.equals("a-z")){
                productList.sort(new ProductComparatorByName());
            }
            if (filterParameter.equals("z-a")){
                productList.sort(new ProductComparatorByNameReversed());
            }
            if (filterParameter.equals("price")){
                productList.sort(new ProductComparatorByPrice());
            }
            if (filterParameter.equals("date")){
                productList.sort(new ProductComparatorByDate());
            }
        }
    }

    public static List<?> getPagination(List<?> productList, int currentPage, int pageSize) {
        int startPoint = (currentPage - 1) * pageSize;
        int pageGap;
        int productListSize = productList.size();
        if (productListSize < (currentPage * pageSize)) {
            pageGap = productListSize - (currentPage - 1) * pageSize;
        } else {
            pageGap = pageSize;
        }
        return productList.subList(startPoint, startPoint + pageGap);
    }

    public static int[] getPages(List<?> productList, int pageSize) {
        int amountOfProducts = productList.size();
        int totalAmountOfPages = amountOfProducts % pageSize == 0 ? amountOfProducts / pageSize : amountOfProducts / pageSize + 1;
        int[] pagesArray = new int[totalAmountOfPages];
        for (int i = 0; i < totalAmountOfPages; i++) {
            pagesArray[i] = i + 1;
        }
        return pagesArray;
    }
}
