import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class HttpServer {
    static public String message = " ";

    public static void mainHTTP() throws Throwable {
        ServerSocket ss = new ServerSocket(8087);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();

                writeResponse(message);
                System.err.println(message);
                message = "";
            } catch (Throwable t) {
                System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ArrayList<String> st = new ArrayList<String>();


            while (true) {
                String s = br.readLine();
                if (s.length() > 13) {
                    if (s.substring(0, 13).equals("Content-Type:")) {

                        if (s.charAt(14) == '*') {
                            st.add(" ");
                            s = s.substring(s.indexOf('*') + 1, s.length());
                            int i = 0;
                            String ss = s;
                            while (true) {
                                if (ss.indexOf('*') != -1) {
                                    ss = ss.substring(ss.indexOf('*') + 1, ss.length());
                                    i++;
                                } else {
                                    break;
                                }
                            }

                            if (i == 3) {
                                String area = s.substring(0, 3);
                                s = s.substring(4, s.length());
                                String street = s.substring(0, s.indexOf('*'));
                                s = s.substring(s.indexOf('*') + 1, s.length());
                                String house = s.substring(0, s.indexOf('*'));
                                s = s.substring(s.indexOf('*') + 1, s.length());
                                String[] mas = {"", area, street, house};
                                Action.Advert(mas, s);
                            } else if (i == 2) {
                                String area = s.substring(0, 3);
                                s = s.substring(4, s.length());
                                String street = s.substring(0, s.indexOf('*'));
                                s = s.substring(s.indexOf('*') + 1, s.length());
                                String[] mas = {"", area, street};
                                Action.Advert(mas, s);
                            } else {
                                String area = s.substring(0, 3);
                                s = s.substring(4, s.length());
                                String[] mas = {"", area};
                                Action.Advert(mas, s);
                            }
                            break;
                        }
                        else if (s.charAt(14) == '|'){
                            message = Action.Iystal();
                            break;

                        }else {
                            String area = s.substring(14, s.length()).substring(0, 3);
                            String message = s.substring(17, s.length());
                            Action.Information(area, message);
                            break;
                        }
                    }
                    //System.err.println(s);
                    if (s == null || s.trim().length() == 0) {
                        break;
                    }
                }
            }
        }
    }
}