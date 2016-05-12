import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;


public class Programm {

    public static final String URL="jdbc:mysql://localhost:3306/spb";
    public static final String USERNAME="root";
    public static final String PUSSWORD=".12345qwert.";

    static public void main(String[] arg) throws Exception{
        ///CreateBD.CreateStritTable("\\\\LLL\\Users\\Public\\Documents\\1.txt","nev");

        /*try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PUSSWORD); Statement statement = connection.createStatement()){


        }
        catch (SQLException e){
            e.printStackTrace();
        }*/

        try {
            HttpServer.mainHTTP();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
