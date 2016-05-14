package client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sergei on 13.05.2016.
 */
public class Client {

   public static void main(String[] args) throws IOException {
        System.err.println(ck());
    }
    public static String ck () {
        String resultJson = "";
        try{
            URL url = new URL("http://25.47.73.73:8087/");
            String query = "12345";
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //urlConnection.setRequestProperty("Advert", "");
            //urlConnection.setRequestProperty("nev", "");
            //urlConnection.setRequestProperty("1", "");
            urlConnection.setRequestProperty("787/5", "");
            urlConnection.setRequestProperty("Content-Type", "|");
            //urlConnection.setRequestProperty("Не бует горячей воды", "");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            try (OutputStream output = urlConnection.getOutputStream()) {
                output.write(query.getBytes("UTF-8"));
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            System.err.println(resultJson);
        }
        catch (Exception e)
        {

        }

        return resultJson;
    }

}
