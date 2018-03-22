package test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePhoneNumber {
  public static void main(String[] argv) {

      String sPhoneNumber = "6058889999";
      //String sPhoneNumber = "605-88899991";
      //String sPhoneNumber = "605-888999A";

      Pattern pattern = Pattern.compile("\\d{10}");
      Matcher matcher = pattern.matcher(sPhoneNumber);

      if (matcher.matches()) {
    	  System.out.println("Phone Number Valid");
      }
      else
      {
    	  System.out.println("Phone Number must be in the form XXX-XXXXXXX");
      }
 }
}