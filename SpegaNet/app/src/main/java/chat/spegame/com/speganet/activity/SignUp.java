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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import chat.spegame.com.speganet.R;
import model.Users;
import utils.EnumDatas;

public class SignUp extends AppCompatActivity implements View.OnClickListener{


    EditText codeText,phoneText;
    Toolbar toolbar;
    Button saveButton;
    JSONObject user=new JSONObject();
    String country="India";
    TextView statusLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.signup);
         Spinner spinner = (Spinner) findViewById(R.id.spinner);
         codeText=(EditText)findViewById(R.id.editText);
         phoneText=(EditText)findViewById(R.id.editText2);
         saveButton=(Button)findViewById(R.id.button2);
         statusLabel=(TextView)findViewById(R.id.textView6);
         statusLabel.setText("");
         initToolBar();
         fillSpinner(spinner);
         saveButton.setOnClickListener(this);


    }

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
        codeText.setEnabled(false);
    }


    public boolean validatePhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
           return  true;
        }
        else
        {
           return false;
        }
    }


    public void fillSpinner(Spinner spinner){
        Map hashMap=new HashMap();
        final List<String> names=new ArrayList<>();
        final List<String> codes=new ArrayList<>();
        String[] isoCountries = Locale.getISOCountries();
        initToolBar();
         for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                names.add(name);
                codes.add(code);

            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner, names);
        dataAdapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 codeText.setText(codes.get(position) );
                 country=names.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(validatePhoneNumber(phoneText.getText().toString().trim())==false){
            Toast.makeText(getApplicationContext(),"Please enter valid phone number",Toast.LENGTH_LONG).show();
        }
        else{

       try {
           JSONObject postData = new JSONObject();
           postData.put("action","insert");
           postData.put("fullName","FullName");
           postData.put("phoneNumber",phoneText.getText().toString().trim());
           postData.put("loginDevice",Build.MODEL+" "+Build.DEVICE);
           postData.put("emailId","avadharwebworld@gmail.com");
           postData.put("country",country);
           postData.put("countryCode",codeText.getText().toString().trim());
           user.put("user", postData);
           SignUpWebCall webCall=new SignUpWebCall();
           String params[]=new String[]{user.toString()};
           webCall.execute(params);



       }catch (Exception exception){
exception.printStackTrace();
       }


        }
      }

 class  SignUpWebCall extends AsyncTask<String,Void,String> {

     private ProgressDialog dialog;
     @Override
     protected void onPreExecute() {
         dialog = new ProgressDialog(SignUp.this);
         dialog.setMessage("Logging..");
         dialog.setTitle("SpegaNet");
         dialog.show();
     }


     @Override
     protected void onPostExecute(String response) {
         try {
             JSONObject reJsonObject = new JSONObject(response.toString());
             JSONObject statusJson=reJsonObject.getJSONObject("result");
             String status=statusJson.getString("status").toString().trim();
             statusLabel.setText(status);
             statusLabel.setTextColor(Color.RED);
             if(status.equals("successs")){
                 Intent homeIntent=new Intent(SignUp.this,ConfirmOtp.class);
                 homeIntent.putExtra("phoneId", phoneText.getText().toString());
                 ArrayList<String> objectsUsers=new ArrayList<String>();
                 objectsUsers.add("FullName");
                 objectsUsers.add(phoneText.getText().toString().trim());
                 objectsUsers.add(Build.MODEL + " " + Build.DEVICE);
                 objectsUsers.add(country);
                 objectsUsers.add(codeText.getText().toString().trim());
                 objectsUsers.add("avadharwebworld@gmail.com");
                 homeIntent.putStringArrayListExtra("user",objectsUsers);
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

}
