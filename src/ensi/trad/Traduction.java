package ensi.trad;


import java.util.Locale;
import java.util.ResourceBundle;


public class Traduction {

    private static ResourceBundle gameBundle;

    public static String get(String key){

        return gameBundle.getString(key);

    }

    /**
     * Set language of the translation
     * @param language the language
     */
    public static void setLanguage(Locale language) {

        gameBundle = ResourceBundle.getBundle("language/game", language);

    }

    /**
     * Get the current language
     * @return current language
     */
    public static String getLanguage(){
        if(gameBundle != null)
            return gameBundle.getLocale().getLanguage();

        return "";
    }

    /**
     * Change the language used
     * @param langue the new language
     */
    public static void changeLanguage(String langue){
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
