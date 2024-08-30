package lk.ijse.gdse67.demo;

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
import lk.ijse.gdse67.demo.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet( value = "/demo")
public class HelloServlet extends HttpServlet {

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

    static String SAVE_STUDENT ="INSERT INTO customer(id,name,city,telephone) VALUES(?,?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO studentDto = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        try {
            PreparedStatement ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1,studentDto.getId());
            ps.setString(2,studentDto.getName());
            ps.setString(3,studentDto.getCity());
            ps.setString(4,studentDto.getTelephone());

            if (ps.executeUpdate()>0){
                resp.getWriter().write("saved");
            }else {
                resp.getWriter().write("not");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}