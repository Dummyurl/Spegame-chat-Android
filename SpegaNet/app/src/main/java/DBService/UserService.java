package DBService;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import model.Users;
import utils.Application;
import utils.DBHelper;

public class UserService {
DBHelper dbHelper;

  public  UserService(Context context){
        dbHelper= Application.dbHelper;

    }

    public long insertUser (Users users) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", users.getFullname());
        contentValues.put("phonenumber",  users.getPhonenumber());
        contentValues.put("emailid", users.getEmailid());
        contentValues.put("country", users.getCountry());
        contentValues.put("countrycode",  users.getCountry());
        contentValues.put("logindevice",  users.getLogindevice());
        contentValues.put("status",  users.getStatus());
        contentValues.put("otp", users.getOtp());
       long reply= db.insert("contacts", null, contentValues);
        return reply;
    }
} 
