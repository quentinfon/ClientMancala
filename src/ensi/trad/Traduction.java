package ensi.trad;


import java.util.Locale;
import java.util.ResourceBundle;


public class Traduction {

    private static ResourceBundle gameBundle;

    public static String get(String key){

        return gameBundle.getString(key);

    }

    public static void setLanguage(Locale language) {

        gameBundle = ResourceBundle.getBundle("language/game", language);

    }

    public static String getLanguage(){
        return gameBundle.getLocale().getLanguage();
    }

}
