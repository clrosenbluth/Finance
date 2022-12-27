package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC
{
    public static void main(String[] args)
    {

        try
        {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//  todo: test without class.forname
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance",
                    "root",
                    "");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from transactions");

            while (resultSet.next())
            {
                System.out.println(resultSet.getString("vendor"));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
