package lk.ijse.gdse67.demo.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.gdse67.demo.bo.CustomerBO;
import lk.ijse.gdse67.demo.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet( value = "/demo")
public class CustomerController extends HttpServlet {

    CustomerBO customerBO = new CustomerBO();

    Connection connection;
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            var cdx = new InitialContext();
            DataSource pool = (DataSource) cdx.lookup("java:comp/env/jdbc/studentRegistration");//methana gana resourse eka onama ekak wenna puluwan eka api narrow cast krnawa dta source ekak wdiyata

            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        try (var writer = resp.getWriter()){
            boolean saved = customerBO.saveCustomer(customerDTO, connection);

            if (saved){
                writer.write("saved");
            }else {
                writer.write("not saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}