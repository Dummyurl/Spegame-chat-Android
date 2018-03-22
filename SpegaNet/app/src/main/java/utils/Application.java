package utils;

import android.content.SharedPreferences;

public class Application extends android.app.Application {

    public static final String PREF_NAME="spega_pref";
    public static     SharedPreferences sp=null;

  public static     DBHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);



            dbHelper = new DBHelper(getApplicationContext());

    }
}
