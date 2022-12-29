package lib;

import java.sql.*;
import java.time.LocalDate;

public class MyJDBC
{
    public static void main(String[] args)
    {

        try
        {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//  todo: insert password, but do not push
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance",
                    "root",
                    "");

            // making a statement
            /*Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transactions");
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("vendor"));
            }*/

            // using a stored procedure: get_all_transactions()
            // todo: use the result processing method used for the next SP
            /*String query = "{CALL get_all_transactions()}";
            CallableStatement stmt = connection.prepareCall(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                System.out.printf("%s - %s%n",
                        rs.getString("vendor"),
                        rs.getString("quantity"));
            }*/

            // Using stored procedure: insert_transaction()
            String vendor = "IBM";
            Float quantity = 200.5f;
            String type = "spot";
            String currency = "ILS";
            Float rate = 35.5f;
            LocalDate localDate = LocalDate.of(2014, 9, 11);
            Date maturity = Date.valueOf(localDate);
            String query = "{CALL insert_transaction(?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(query);
            stmt.setString(1, vendor);
            stmt.setFloat(2, quantity);
            stmt.setString(3, type);
            stmt.setString(4, currency);
            stmt.setFloat(5, rate);
            stmt.setDate(6, maturity);

            boolean hadResults = stmt.execute();

            while (hadResults)
            {
                ResultSet rs = stmt.getResultSet();
                System.out.println("I have a result! " + rs);
                hadResults = stmt.getMoreResults();
            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
