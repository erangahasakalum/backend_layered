package lk.ijse.gdse67.demo.bo;

import lk.ijse.gdse67.demo.dao.CustomerDAO;
import lk.ijse.gdse67.demo.dto.CustomerDTO;
import lk.ijse.gdse67.demo.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBO {
    CustomerDAO customerDAO = new CustomerDAO();

    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException {
       return customerDAO.save(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getCity(),customerDTO.getTelephone()),connection);
    }
}
