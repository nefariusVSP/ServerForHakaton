import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class HttpServer {
    static public String message;
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

            boolean t = false;
            boolean er = true;
            while(true) {
                String s = br.readLine();
                if (er)
                    if (s.equals("ERROR: ")){
                        er = false;
                        Action.Actions(st);
                    }
                    if (t == false){
                        t = true;
                    }
                    else {
                        if (s.length() -2 > 0)
                        st.add( s.substring(0,s.length()-2));

                    }
                System.err.println(s);
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}