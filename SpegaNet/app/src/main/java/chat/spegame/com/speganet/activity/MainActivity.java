package chat.spegame.com.speganet.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import chat.spegame.com.speganet.R;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
       final TextView privacy=(TextView)findViewById(R.id.textView3);
        Spanned html = Html.fromHtml("Terms and Policy <br />" +
                "<a href='http://www.spegame.com/policy.php'>Terms and Policy</a>");
        privacy.setMovementMethod(LinkMovementMethod.getInstance());
        // Set TextView text from html
        privacy.setText(html);
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        Toast.makeText(getApplicationContext(),mPhoneNumber,Toast.LENGTH_LONG).show();
        privacy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.spegame.com/policy.php"));
                startActivity(intent);
            }
        });

        next=(Button)findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp=new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUp);


            }
        });
        initToolBar();
    }



    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
 toolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.toolbar_logo);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
              //          Toast.makeText(MainActivity.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }


}
