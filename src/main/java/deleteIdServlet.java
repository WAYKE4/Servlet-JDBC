import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/delete")

public class deleteIdServlet extends HttpServlet {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/usersJdbc";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "root";
    static final String DELETE_USER_FROM_USERS = "DELETE FROM users WHERE users_id=?";
    Connection connection;
    PrintWriter printWriter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_FROM_USERS);
            Long id = Long.valueOf(req.getParameter("id"));
            statement.setLong(1, id);
            if (statement.executeUpdate() == 1) {
                resp.setContentType("text/html");
                printWriter = resp.getWriter();
                printWriter.write("SUCCESS!");
            } else {
                printWriter.write("Something wrong!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
}
