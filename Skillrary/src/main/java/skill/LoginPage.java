package skill;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Driver;

public class LoginPage extends HttpServlet{
 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  String browserUsername = req.getParameter("regno");
  String browserPassword = req.getParameter("pass");
  
  String htmlResponse=null;
  Connection connection=null;
  try {
   //step 1: load/register the mysql database
   Driver driver = new Driver();
   DriverManager.registerDriver(driver);
   
   //step 2: connect to the database
   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","root");
   
   //step 3: issue the database querry
   Statement statement = connection.createStatement();
   
   //step 4: execute the querry
   ResultSet result = statement.executeQuery("select * from student_info");
   while(result.next()) {
    String dbUserName = result.getString(2);
    if(browserUsername.equals(dbUserName)) {
     htmlResponse="<html>"
       + "   <title>Homepage</title>"
       + "   <body>"
       + "       <h1>"
       + "          <font color='red'>welcome to home Page</font>"
       + "        </h1>"
       + "<a href='http://localhost:8080/Skillrary/login.html'>Login</a>"
       + "   </body>"
       + "</html>";
     break;
    }else {
     htmlResponse="<html>"
       + "   <title>Homepage</title>"
       + "   <body>"
       + "       <h1>"
       + "          <font color='red'>user is not exist, Retry!!</font>"
       + "        </h1>"
       + "     <a href='http://localhost:8080/Skillrary/login.html'>Login</a>"
       + "   </body>"
       + "</html>";
    }
   }
  } catch (SQLException e) {
   System.out.println("handled"); ;
  }finally {
   try {
    connection.close();
   } catch (SQLException e) {
    
   }
  }
  resp.setContentType("text/html");
  resp.getWriter().print(htmlResponse);
 }
}