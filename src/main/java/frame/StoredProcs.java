package frame;

import java.sql.*;
import java.util.ArrayList;

public class StoredProcs
{
    private Connection conn;

    public StoredProcs(Connection conn)
    {
        this.conn = conn;
    }

    public ArrayList<String[]> getAllTransactions() throws SQLException
    {
        String query = "{CALL get_all_transactions()}";
        CallableStatement stmt = conn.prepareCall(query);
        ResultSet rs = stmt.executeQuery();

        ArrayList<String[]> results = new ArrayList<String[]>();
        while (rs.next())
        {
            String vendor = null;
            try
            {
                vendor = rs.getString("vendor");
            } catch (Exception ignored)
            {}
            Date maturity = null;
            try
            {
                maturity = rs.getDate("maturity");
            } catch (Exception ignored)
            {}
            results.add(new String[] {
                    vendor,
                    String.valueOf(rs.getFloat("quantity")),
                    rs.getString("t_type"),
                    rs.getString("currency"),
                    String.valueOf(rs.getFloat("rate")),
                    String.valueOf(maturity)
            });
        }

        return results;
    }

    public void insertTransaction(String vendor,
                                  Float quantity,
                                  String type,
                                  String currency,
                                  Float rate,
                                  Date maturity) throws SQLException
    {
        String query = "{CALL insert_transaction(?,?,?,?,?,?,?)}";
        CallableStatement stmt = conn.prepareCall(query);
        stmt.setString(1, vendor);
        stmt.setFloat(2, quantity);
        stmt.setString(3, type);
        stmt.setString(4, currency);
        stmt.setFloat(5, rate);
        stmt.setDate(6, maturity);

        stmt.execute();
    }
}
