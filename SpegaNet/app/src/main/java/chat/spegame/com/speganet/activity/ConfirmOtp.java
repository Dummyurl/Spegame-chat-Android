package chat.spegame.com.speganet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import DBService.UserService;
import chat.spegame.com.speganet.R;
import model.Users;
import utils.EnumDatas;

public class ConfirmOtp extends AppCompatActivity {
    EditText otpText;
    Button otpButton,resendButton;
    String phoneId;
ArrayList<String> passObjects=new ArrayList<>();
    Toolbar toolbar;

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.toolbar_logo);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }

        );

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cofirm_otp_layout);
        Bundle paramaData=getIntent().getExtras();
        phoneId=paramaData.getString("phoneId");
        otpButton=(Button)findViewById(R.id.button3);
        resendButton=(Button)findViewById(R.id.button4);
        otpText=(EditText)findViewById(R.id.editText3);
        passObjects=getIntent().getStringArrayListExtra("user");
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject user = new JSONObject();
                    user.put("action","confirmotp");
                    user.put("phoneId",phoneId);
                    user.put("otp",otpText.getText().toString());
                    JSONObject request=new JSONObject();
                    request.put("user",user);
                    String[] paramas=new String[]{request.toString()};
                    OtpWebCall otpWebCall = new OtpWebCall();
                    otpWebCall.execute(paramas);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });


        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject user = new JSONObject();
                    user.put("action","resendotp");
                    user.put("phoneId", phoneId);
                     JSONObject request=new JSONObject();
                    request.put("user",user);
                    String[] paramas=new String[]{request.toString()};
                    ResendOtpWebCall resendOtpWebCall =new ResendOtpWebCall();
                    resendOtpWebCall.execute(paramas);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }
    class  OtpWebCall extends AsyncTask<String,Void,String> {

        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ConfirmOtp.this);
            dialog.setMessage("on Loading..");
            dialog.setTitle("SpegaNet");
            dialog.show();
        }


        @Override
        protected void onPostExecute(String response) {



            try{
                JSONObject resp=new JSONObject(response);
                boolean reply = Boolean.parseBoolean(resp.getString("status"));
               if(reply){

                   Users users=new Users();
                   users.setFullname("FullName");
                   users.setPhonenumber(phoneId);
                   users.setEmailid(passObjects.get(5));
                   users.setCountry(passObjects.get(3));
                   users.setCountrycode(passObjects.get(4));
                   users.setLogindevice(passObjects.get(2));
                   users.setStatus(EnumDatas.Constants.ACTIVE.getString().toString());
                   long replyDb=new UserService(getApplicationContext()).insertUser(users );
                   Toast.makeText(getApplicationContext(),""+replyDb,Toast.LENGTH_LONG).show();
                   Intent homeIntent=new Intent(ConfirmOtp.this,HomeScreen.class);
                   homeIntent.putExtra("phoneId",phoneId);
                   startActivity(homeIntent);
               }


            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            dialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String response=doPost(params[0]);
            return response;
        }


        public String doPost(String parameters) {
            String url = EnumDatas.serverName + EnumDatas.userServlet;
            try {
                URL obj = new URL(url);
                HttpURLConnection con=(HttpURLConnection)obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setDoOutput(true);
                con.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
                return response.toString();

            } catch (Exception exception) {
                exception.printStackTrace();
                return "";

            }


        }

    }


    class  ResendOtpWebCall extends AsyncTask<String,Void,String> {

        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ConfirmOtp.this);
            dialog.setMessage("on Loading..");
            dialog.setTitle("SpegaNet");
            dialog.show();
        }


        @Override
        protected void onPostExecute(String response) {

            try{

                 JSONObject object=new JSONObject(response);
                 String status=object.getString("status");
                 Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            dialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String response=doPost(params[0]);
            return response;
        }


        public String doPost(String parameters) {
            String url = EnumDatas.serverName + EnumDatas.userServlet;
            try {
                URL obj = new URL(url);
                HttpURLConnection con=(HttpURLConnection)obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setDoOutput(true);
                con.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
                return response.toString();

            } catch (Exception exception) {
                exception.printStackTrace();
                return "";

            }


        }

    }







}
