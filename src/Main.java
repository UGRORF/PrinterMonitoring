import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Printer> printers = new ArrayList<>();
        try {
            List<String> strings = Files.readAllLines(Paths.get("..\\printersIp.txt"));

            for (int i = 0; i < strings.size(); i++) {
                String ip = strings.get(i);
                printers.add(new Printer(ip));
            }

            PrintersDB javaToMySQL = new PrintersDB(printers);
//            Mail mail = new Mail();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean isAvailable(String ip){
        boolean available = false;
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ip, 9100), 1000);
            s.close();
            available = true;
        } catch (IOException e) {
            available = false;
        }
        return available;
    }
}