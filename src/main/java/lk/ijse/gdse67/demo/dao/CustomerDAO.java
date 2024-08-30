package lk.ijse.gdse67.demo.dao;

import lk.ijse.gdse67.demo.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {

    static String SAVE_STUDENT ="INSERT INTO customer(id,name,city,telephone) VALUES(?,?,?,?)";
    public boolean save(Customer customer, Connection connection) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(SAVE_STUDENT);
        ps.setString(1,customer.getId());
        ps.setString(2,customer.getName());
        ps.setString(3,customer.getCity());
        ps.setString(4,customer.getTelephone());

        return ps.executeUpdate()>0;
    }
}
