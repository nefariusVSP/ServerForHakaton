import sun.util.resources.CalendarData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
                
                break;
            case 4:
                break;
        }
    }

}