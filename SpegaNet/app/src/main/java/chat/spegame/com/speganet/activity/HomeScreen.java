package chat.spegame.com.speganet.activity;

import android.app.ActionBar;
import android.app.SearchManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import chat.spegame.com.speganet.R;
import model.Users;
import surfaceholders.Recycler_News;

public class HomeScreen extends AppCompatActivity {
    RecyclerView recyclerView_news;
    List<Users> datanews;
    Toolbar toolbar,toolbar1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initToolBar();
        recyclerView_news = (RecyclerView) findViewById(R.id.recycler_news);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView_news.setLayoutManager(llm);
        recyclerView_news.setHasFixedSize(true);
       // initialize();
        datanews=new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Users u=new Users();
            u.setFullname(name);
            u.setPhonenumber(phoneNumber);
            datanews.add(u);
        }
        phones.close();

         Recycler_News adapter=new Recycler_News(HomeScreen.this,datanews);
        recyclerView_news.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
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
     }


}
