import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {


    public static Connection connect(){
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return null;
        }
        Connection c = null;
        try
        {
            c = DriverManager.getConnection("org.hsqldb.jdbc.JDBCDriver", "SA", "");

        }catch(SQLException e){
            System.err.println("Error");

        }
        return c;
    }

    public static void createTable(Connection c) throws SQLException{
        Statement stmt =  c.createStatement();
        stmt.executorUpdate("CREATE TABLE tablea(id INTEGER, name VAQRCHAR(64));");
    }

    public static void fillTablewithData(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.ececutorUpdate(" INSERT INTO tabela VALUES (1, 'Alicja');");
        stmt.ececutorUpdate(" INSERT INTO tabela VALUES (1, 'Marek');");
        stmt.ececutorUpdate(" INSERT INTO tabela VALUES (1, 'Anna');");
    }

    public static void initDB(Connection c) throws SQLException{
        createTable(c);
        fillTablewithData(c);
    }


    public static void main(String[] args) throws SQLException {
        Connection c = connect();
        try{
            initDB(c);

        }catch(SQLException ex){
            System.err.println("Problemy");
            ex.printStackTrace();
            try{
                c.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return;
        }
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tabela");
        while(rs.next())
        {
            System.out.println(rs.getInt("id") +", " + rs.getString("name") );

        }
        c.close();
    }


}