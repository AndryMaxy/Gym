package dao.impl;

import dao.AppointmentDAO;
import entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDAOImpl extends AppointmentDAO<Product> {

    private static final String SELECT_ALL = "SELECT ProductId, Name FROM Product";
    private static final String SELECT_BY_USER_ID =
            "SELECT p.Name, a.GramInDay FROM Product p JOIN ProductAppointment a on p.ProductId = a.ProductId WHERE a.UserId = ?";
    private static final String ADD = "INSERT INTO ProductAppointment ( UserId, ProductId, GramInDay ) VALUE ( ?, ?, ? )";


    private static class ProductDAOImplHolder{
        static final ProductDAOImpl INSTANCE = new ProductDAOImpl();
    }

    private ProductDAOImpl(){}

    public static ProductDAOImpl getInstance() {
        return ProductDAOImplHolder.INSTANCE;
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getByUserIdQuery() {
        return SELECT_BY_USER_ID;
    }

    @Override
    protected void handleAllResult(List<Product> list, ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("ProductId");
        String name = resultSet.getString("Name");
        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        list.add(product);
    }

    @Override
    protected void handleByUserIdResult(List<Product> list, ResultSet resultSet) throws SQLException {
        Product product = new Product();
        String name = resultSet.getString("Name");
        int gramInDay = resultSet.getInt("GramInDay");
        product.setName(name);
        product.setGramInDay(gramInDay);
        list.add(product);
    }

    @Override
    protected String addAppointment() {
        return ADD;
    }

    @Override
    protected void handleAdd(int userId, Product product, PreparedStatement statement) throws SQLException {
        statement.setInt(1, userId);
        statement.setInt(2, product.getId());
        statement.setInt(3, product.getGramInDay());
        statement.addBatch();
    }
}
