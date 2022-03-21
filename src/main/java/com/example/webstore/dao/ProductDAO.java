package com.example.webstore.dao;

import com.example.webstore.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductDAO implements Crud<Product> {

    private static final Logger logger = Logger.getLogger("ProductDAOLogger");
    private static ProductDAO instance;

    public static synchronized ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    @Override
    public Product create(Product entity) {
        String sql = "INSERT INTO products (name, price, date, color) VALUES (?,?,?,?)";
        String sqlGetLast = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql);
             Statement statementGetLast = con.createStatement()) {
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setTimestamp(3, entity.getDate());
            statement.setString(4, entity.getColor());
            statement.execute();
            ResultSet set = statementGetLast.executeQuery(sqlGetLast);
            set.next();
            entity.setId(set.getInt("id"));
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_CREATE_PRODUCT.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return entity;
    }

    @Override
    public Optional<Product> findById(int id) throws Exception {
        Product product = new Product();
        String find = "SELECT * FROM products WHERE id=?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(find)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            product.setColor(resultSet.getString("color"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            product.setTotalAmount(resultSet.getInt("total_amount"));
            product.setId(id);
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_PRODUCT_BY_ID.toString());
            product = null;
            System.out.println(e.getMessage());
        } finally {
            DataSource.closeConnection(con);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        Connection con = DataSource.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet productSet = statement.executeQuery("SELECT * FROM products")) {
            while (productSet.next()) {
                Product product = new Product();
                products.add(setProductParameters(product, productSet));
            }
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_ALL_PRODUCTS.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return products;
    }

    static Product setProductParameters(Product product, ResultSet productSet) throws SQLException {
            product.setId(productSet.getInt("id"));
            product.setName(productSet.getString("name"));
            product.setPrice(productSet.getDouble("price"));
            product.setTotalAmount(productSet.getInt("total_amount"));
            product.setDate(productSet.getTimestamp("date").toLocalDateTime());
            product.setColor(productSet.getString("color"));
            return product;
    }

    @Override
    public void update(Product entity) {
        String sql = "UPDATE products SET name = ?, price = ?, date = ?, color = ?, total_amount = ? WHERE id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setString(4, entity.getColor());
            statement.setInt(5, entity.getTotalAmount());
            statement.setInt(6, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_UPDATE_PRODUCT.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_DELETE_PRODUCT.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
    }
}
