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
        if(gameBundle != null)
            return gameBundle.getLocale().getLanguage();

        return "";
    }

    public static
    void changeLanguage(String langue){
        switch (langue){
            case "FR":
                Traduction.setLanguage(Locale.FRENCH);
                break;
            case "EN" :
                Traduction.setLanguage(Locale.ENGLISH);
                break;
            default:
                break;
        }
    }

}
