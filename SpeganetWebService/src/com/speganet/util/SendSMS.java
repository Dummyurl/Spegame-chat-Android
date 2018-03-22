package com.speganet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {
	
	public static SendSMS sms=new SendSMS();
	public static String smsUrl="http://smsalertbox.com/api/sms.php?uid=7468696d6f746879&pin=318b2b3f7edfbf0485749212a7db83f5&sender=AVADHR&route=5&tempid=2&pushid=1";
	 
	
	public static SendSMS getSms() {
		return sms;
		
 	}
public int sendOtp(String phoneNumber,String otpMessage){
	int result=0;
	
	 try {
          URL url = new URL(smsUrl+"&mobile="+phoneNumber+"&message="+"Activation%20code%20:"+otpMessage+"%20Spegame.com");
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setDoOutput(true);
         connection.setRequestMethod("POST");
         StringBuilder response = new StringBuilder();
         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         String line;
         while ( (line = br.readLine()) != null)
             response.append(line);
         br.close();
       
         result=Integer.parseInt(response.toString());
     } catch ( Exception e) {
    	 e.printStackTrace();
        result=0;
     }
	 return result;
	
}
	public static void main(String[] args) {
		SendSMS sendSMS=getSms();
		sendSMS.sendOtp("9747102150", "Activation%20code%20:"+OneTimePassword.geOneTimePassword().getOTP()+"%20Spegame.com");
	}
	 

}
