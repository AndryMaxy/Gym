package dao.impl;

import dao.AppointmentDAO;
import entity.ProductAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductAppointmentDAOImpl extends AppointmentDAO<ProductAppointment> {

    private static final String SELECT_ALL = "SELECT ProductId, Name FROM Product";
    private static final String SELECT_BY_USER_ID =
            "SELECT p.Name, a.GramInDay FROM Product p JOIN ProductAppointment a on p.ProductId = a.ProductId WHERE a.BookingId = ?";
    private static final String INSERT = "INSERT INTO ProductAppointment ( BookingId, ProductId, GramInDay ) VALUE ( ?, ?, ? )";


    private static class ProductDAOImplHolder{
        static final ProductAppointmentDAOImpl INSTANCE = new ProductAppointmentDAOImpl();
    }

    private ProductAppointmentDAOImpl(){}

    public static ProductAppointmentDAOImpl getInstance() {
        return ProductDAOImplHolder.INSTANCE;
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getByBookingIdQuery() {
        return SELECT_BY_USER_ID;
    }

    @Override
    protected void handleAllResult(List<ProductAppointment> list, ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("ProductId");
        String name = resultSet.getString("Name");
        ProductAppointment productAppointment = new ProductAppointment();
        productAppointment.setId(productId);
        productAppointment.setName(name);
        list.add(productAppointment);
    }

    @Override
    protected void handleByBookingIdResult(List<ProductAppointment> list, ResultSet resultSet) throws SQLException {
        ProductAppointment productAppointment = new ProductAppointment();
        String name = resultSet.getString("Name");
        int gramInDay = resultSet.getInt("GramInDay");
        productAppointment.setName(name);
        productAppointment.setGramInDay(gramInDay);
        list.add(productAppointment);
    }

    @Override
    protected String addAppointmentQuery() {
        return INSERT;
    }

    @Override
    protected void handleAdd(int bookingId, ProductAppointment productAppointment, PreparedStatement statement) throws SQLException {
        statement.setInt(1, bookingId);
        statement.setInt(2, productAppointment.getId());
        statement.setInt(3, productAppointment.getGramInDay());
        statement.addBatch();
    }
}
