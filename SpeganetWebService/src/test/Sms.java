package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
public class Sms {
    public static void main(String[] args) {
        try {
            
            URL url = new URL("http://smsalertbox.com/api/sms.php?uid=7468696d6f746879&pin=318b2b3f7edfbf0485749212a7db83f5&sender=AVADHR&route=5&tempid=2&mobile=9747102150&message=Activation%20code%20:34343%20Spegame.com&pushid=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
       
           
            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ( (line = br.readLine()) != null)
                response.append(line);
            br.close();
          
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}