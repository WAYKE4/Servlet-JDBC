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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/get")
public class getIdServlet extends HttpServlet {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/usersJdbc";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "root";
    static final String GET_INFO_USER_FROM_ID = "SELECT * FROM users WHERE users_id = ?";
    Connection connection;
    PrintWriter printWriter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id;
            String userName;
            String userLogin;
            String userPassword;
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(GET_INFO_USER_FROM_ID);
            String nameFromeUrl = req.getParameter("id");
            statement.setLong(1, Long.parseLong(nameFromeUrl));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
                userName = resultSet.getString(2);
                userLogin = resultSet.getString(3);
                userPassword = resultSet.getString(4);
                resp.setContentType("text/html");
                printWriter = resp.getWriter();
                printWriter.write("ID: " + id + "<br>");
                printWriter.write("USERNAME: " + userName + "<br>");
                printWriter.write("USERLOGIN: " + userLogin + "<br>");
                printWriter.write("USERPASSWORD: " + userPassword + "<br>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        printWriter.close();
    }
}
