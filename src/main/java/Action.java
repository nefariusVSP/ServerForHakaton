import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Action {

    public static void Actions (ArrayList<String> value){
        String action = value.get(0);
        switch (action){
            case "Registration":
                //фамилия, имя, отчество, серия, номер
                int countValue = 5;
                String[] values = new String[countValue];
                for (int i = 0; i < countValue; ++i){
                    values[i] = value.get(i+2);
                }
                if (Registration(values).length != 0){
                    // создать в базе данных в определенной улице учетную запись
                    try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery("SELECT id FROM `area` WHERE `name`= " );
                        //TODO надо доделать , и продумать реистрацию, возможно регистрироваться надо по адресу и паспорту
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "Bid"://заявка
                //TODO нужно занать куда отпровлять заявку.
                break;

            case "Advert"://оповезщение
                // неопределенное количество строк данных
                int count = value.size();

                String[] valueA = new String[count-1];
                for (int i = 0; i < count-1; ++i){
                    valueA[i] = value.get(i);
                }
                Advert(valueA, value.get(count - 1));
                break;
            case "Check": //
                String seria = value.get(1);
                String noemr = value.get(2);
                Check(seria, noemr);
                break;
            case "Information":
                Information(value.get(1),value.get(2));
                break;
        }
    }
    static String[] Registration(String[] value){
        //Создать проверку на хакатоне в каком адресе живет пользователь
        //Вернуть массив 1 фамилия 2 имя 3 отвество 4 серия 5 номер 6 район 7 улица 8 дом 9 корпус 10 квартира
        // Если же не верно вернуть String[0]

        return new String[0];
    }

    static void Bid(String[] value){

    }

    static void Advert(String[] value, String message){
        //Тут должны приходить 1 район, 2 улица, 3 дом
        ResultSet resultSet;
        switch (value.length){
            case 2:
                try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
                    resultSet = statement.executeQuery("SELECT `countStreet` FROM `area` WHERE `id` = \"" + value[1] + "\"");
                    resultSet.next();
                    int count  = resultSet.getInt("countStreet");

                    SimpleDateFormat sec = new SimpleDateFormat("dd.MM.yyyy");
                    String date = sec.format(new java.util.Date());
                    for (int i = 1; i <= count;++i){
                        resultSet = statement.executeQuery("SELECT * FROM `" + value[1] + i+"`");

                        ArrayList<String> seria = new ArrayList<String>();
                        ArrayList<String> nomber = new ArrayList<String>();
                        while (resultSet.next()) {
                            seria.add(resultSet.getString("serialPassport"));
                            nomber.add(resultSet.getString("namberPassport"));
                        }
                        for (int j = 0; j < seria.size(); ++j) {
                            String SQL = "INSERT INTO `message` (`serialPassport`,`namberPassport`,`message`,`date`) VALUE ( \"" +
                                    seria.get(j) + "\",\"" + nomber.get(j) + "\",\"" + message + "\",\"" + date + "\" );";
                            statement.execute(SQL);
                        }
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {

                    resultSet = statement.executeQuery("SELECT * FROM `" + value[1] + value[2] + "`");
                    SimpleDateFormat sec = new SimpleDateFormat("dd.MM.yyyy");
                    String date = sec.format(new java.util.Date());
                    ArrayList<String> seria = new ArrayList<String>();
                    ArrayList<String> nomber = new ArrayList<String>();
                    while (resultSet.next()) {
                        seria.add(resultSet.getString("serialPassport"));
                        nomber.add(resultSet.getString("namberPassport"));
                    }
                    for (int j = 0; j < seria.size(); ++j) {
                        String SQL = "INSERT INTO `message` (`serialPassport`,`namberPassport`,`message`,`date`) VALUE ( \"" +
                                seria.get(j) + "\",\"" + nomber.get(j) + "\",\"" + message + "\",\"" + date + "\" );";
                        statement.execute(SQL);
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
                    String home = value[3].substring(0,value[3].indexOf('/'));
                    String shome = value[3].substring(value[3].indexOf('/')+1 , value[3].length());

                    resultSet = statement.executeQuery("SELECT * FROM `" + value[1] + value[2] + "` WHERE `home` = \"" + home + "\" and `housing` = \"" + shome + "\"");
                    SimpleDateFormat sec = new SimpleDateFormat("dd.MM.yyyy");
                    String date = sec.format(new java.util.Date());
                    while (resultSet.next()){

                        String SQL = "INSERT INTO `message` (`serialPassport`,`namberPassport`,`message`,`date`) VALUE ( \"" +
                                resultSet.getString("serialPassport") + "\",\"" + resultSet.getString("namberPassport") + "\",\"" + message + "\",\"" + date + "\" );";
                        statement.execute(SQL);
                    }

                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    static void Check (String serial, String namber){
        try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `message` WHERE `serialPassport`= \"" + serial + "\" and `namberPassport` = \"" + namber + "\";");
            HttpServer.message = "";
            while (resultSet.next()){
                String message = resultSet.getString("message") + " " + resultSet.getString("date");
                HttpServer.message +=message;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void Information (String area, String message){
        try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
            statement.execute("UPDATE `area` SET `information` =\"" + message +"\" WHERE `id` = \"" + area +"\";" );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public String  Iystal (){
        ResultSet resultSet;
        String result ="";
        try (Connection connection = DriverManager.getConnection(Programm.URL,Programm.USERNAME,Programm.PUSSWORD); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM `zapros`");
            while (resultSet.next()){
                result+= resultSet.getString("idarea") + "*" + resultSet.getString("idstreet") + "*" + resultSet.getString("house") + "*" + resultSet.getString("housing") + "*" + resultSet.getString("flat") + "*" + resultSet.getString("message") + "*";
            }
            resultSet.next();
        }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }
}
