import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



public class CreateBD {
    // служит для заполнения бд таблицами улиц, принемает путь к файлу txt, и префикс района
    static public void CreateStritTable (String path, String preficsNameTable) throws IOException {
        String ful = "";
        String zap1 = "CREATE TABLE `spb`.`";
        String zap2 = "` (\n" +
                "  `home` VARCHAR(4) NOT NULL,\n" +
                "  `housing` VARCHAR(4) NOT NULL,\n" +
                "  `flat` VARCHAR(4) NOT NULL,\n" +
                "  `lastName` VARCHAR(20) NOT NULL,\n" +
                "  `fistName` VARCHAR(20) NOT NULL,\n" +
                "  `midName` VARCHAR(20) NOT NULL,\n" +
                "  `serialPassport` VARCHAR(4) NOT NULL,\n" +
                "  `namberPassport` VARCHAR(6) NOT NULL,\n" +
                "  PRIMARY KEY (`serialPassport`, `namberPassport`));\n";

        try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {

            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            System.err.println(connection.isClosed());
            int i = 1;
            for (String line : lines) {
                statement.execute(zap1 + preficsNameTable + i + zap2);
                //ful += zap1 + preficsNameTable + i + zap2;
                i += 1;
            }
            System.err.println("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
