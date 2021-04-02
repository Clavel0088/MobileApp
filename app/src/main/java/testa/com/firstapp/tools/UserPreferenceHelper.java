package testa.com.firstapp.tools;

import android.content.Context;
import android.content.SharedPreferences;



public class UserPreferenceHelper {


    private static UserPreferenceHelper ourInstance ;
    private SharedPreferences sharedPreferences;

    public static UserPreferenceHelper getInstance(Context context) {

        if (ourInstance == null) {
            ourInstance = new UserPreferenceHelper(context);
        }
        return ourInstance;
    }

    private UserPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
    }

    public void saveString(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public String getString(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

}
