package ee.soidutaja.soidutaja;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Virx on 24.11.2015.
 */
public class SharedPreferencesManager {

    private static final String APP_SETTINGS = "APP_SETTINGS";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String[] readData(Context context) {
//        return getSharedPreferences(context).getString(SOME_STRING_VALUE , null);
        String[] list = new String[4];
        SharedPreferences prefs = getSharedPreferences(context);
        if(prefs != null) {
            list[0] = prefs.getString("userName", "no name defined");
            list[1] = prefs.getString("userId", "no id defined");
            list[2] = prefs.getString("userEmail", "no email defined");
            list[3] = prefs.getString("userGender", "no gender defined");
            return list;
        }
        else {
            return null;
        }
    }

    public static void saveData(Context context, User user) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("userName", user.getName());
        editor.putString("userId", user.getFacebookID());
        editor.putString("userEmail", user.getEmail());
        editor.putString("userGender", user.getGender());
        editor.commit();
    }

    public static void clearData(Context context) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
