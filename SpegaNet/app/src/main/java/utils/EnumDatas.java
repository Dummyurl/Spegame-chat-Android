package utils;

public class EnumDatas {

    public static String serverName="http://192.168.0.4:8080/SpeganetWebService/";
    public static String userServlet="userActions";

    public enum Constants {

        INSERT("insert"),
        ACTIVE("active"),
        INACTIVE("inactive"),
        ONLINE("online"),
        PENDING("pending"),
        UNKNOWN("unknown");




        private String url;

        Constants(String url) {
            this.url = url;
        }

        public String getString() {
            return url;
        }
    }


} 
