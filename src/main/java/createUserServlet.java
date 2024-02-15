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

@WebServlet("/create")
public class createUserServlet extends HttpServlet {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/usersJdbc";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "root";
    static final String INSERT_USER_INTO_USERS = "INSERT INTO users(users_id,username,user_login,user_password)" +
            "VALUES(DEFAULT,?,?,?)";
    Connection connection;
    PrintWriter printWriter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/isCreated.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("name");
        String userLogin = req.getParameter("login");
        String userPassword = req.getParameter("password");

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_INTO_USERS);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userLogin);
            preparedStatement.setString(3, userPassword);
            if (preparedStatement.executeUpdate() == 1) {
                resp.setContentType("text/html");
                printWriter = resp.getWriter();
                printWriter.write("SUCCESS!");
            } else {
                printWriter.write("Something wrong!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        printWriter.close();
        getServletContext().getRequestDispatcher("/database").forward(req, resp);
    }
}