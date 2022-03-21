package com.example.webstore.dao;

import com.example.webstore.model.Order;
import com.example.webstore.model.Product;
import com.example.webstore.model.Status;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrderDAO implements Crud<Order> {

    private static final Logger logger = Logger.getLogger("OrderDAOLogger");
    private static OrderDAO instance;

    public static synchronized OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    @Override
    public Order create(Order entity) {
        String sqlToInsert = "INSERT INTO orders (date, status, user_id) VALUES (?,?,?)";
        String sqlGetLast = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statementToInsert = con.prepareStatement(sqlToInsert);
             Statement statementGetLast = con.createStatement()) {
            statementToInsert.setTimestamp(1, entity.getDate());
            statementToInsert.setString(2, entity.getStatus().toString());
            statementToInsert.setInt(3, entity.getUserId());
            statementToInsert.execute();
            ResultSet set = statementGetLast.executeQuery(sqlGetLast);
            set.next();
            entity.setId(set.getInt("id"));
            setOrderProducts(entity);
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_CREATE_ORDER.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return entity;
    }

//    public void setUsersOrders(Order order) throws SQLException {
//        String sql = "INSERT INTO users_orders (user_id, order_id) VALUES (?, ?)";
//        try (Connection con = DataSource.getConnection()) {
//            con.setAutoCommit(false);
//            try (PreparedStatement statement = con.prepareStatement(sql)) {
//                statement.setInt(1, order.getUserId());
//                statement.setInt(2, order.getId());
//                statement.execute();
//            } catch (SQLException e) {
//                con.rollback();
//                e.printStackTrace();
//            }
//            con.commit();
//        }
//    }

    @Override
    public Optional<Order> findById(int id) throws Exception {
        Connection con = DataSource.getConnection();
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet set = statement.getResultSet();
            set.next();
            return Optional.of(setOrderParameters(set));
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_ORDER_BY_ID.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return Optional.empty();
    }

    public List<Order> findOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Order order = setOrderParameters(set);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_ORDER_BY_USER_ID.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        Connection con = DataSource.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM orders")) {
            while (set.next()) {
                Order order = setOrderParameters(set);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_ALL_ORDERS.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return orders;
    }

    private Order setOrderParameters(ResultSet set) throws SQLException {
        Order order = new Order();
        int orderId = set.getInt("id");
        order.setId(orderId);
        order.setProductMap(getProductsByOrderId(orderId));
        order.setStatus(Status.valueOf(set.getString("status")));
        order.setDate(set.getTimestamp("date").toLocalDateTime());
        order.setId(set.getInt("id"));
        return order;
    }

    public boolean setOrderProducts(Order order) throws SQLException {
        String sql = "INSERT INTO orders_products (order_id, product_id, amount) VALUES (?, ?, ?)";
        Map<Product, Integer> productMap = order.getProductMap();
        Connection con = DataSource.getConnection();
        try {
            con.setAutoCommit(false);
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                for (Map.Entry<Product, Integer> product : productMap.entrySet()) {
                    statement.setInt(1, order.getId());
                    statement.setInt(2, product.getKey().getId());
                    statement.setInt(3, product.getValue());
                    statement.execute();
                }
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_SET_ORDER_PRODUCTS.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return false;
    }


    public Map<Product, Integer> getProductsByOrderId(int orderId) {
        Map<Product, Integer> productList = new HashMap<>();
        String sql = "SELECT id, name, price, date, color, total_amount, amount FROM products JOIN orders_products ON product_id = id WHERE order_id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.execute();
            ResultSet productSet = statement.getResultSet();
            while (productSet.next()) {
                Product product = new Product();
                productList.put(ProductDAO.setProductParameters(product, productSet), productSet.getInt("amount"));
            }
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_GET_PRODUCTS_BY_ORDER_ID.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return productList;
    }

    @Override
    public void update(Order entity) {
        String sql = "UPDATE orders SET status = ?, date = ?, user_id = ? WHERE id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, entity.getStatus().toString());
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setInt(3, entity.getUserId());
            statement.setInt(4, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_UPDATE_ORDER.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
    }
}
