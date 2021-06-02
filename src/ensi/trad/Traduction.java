package ensi.trad;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Traduction {

    public static String YOUR_TURN;
    public static String OPPONENT_TURN;

    public static void setLanguage(Language language) {

        String file = "/language/";

        switch(language) {
            case FR:
                file += "game_fr.properties";
                break;
            case EN:
                file += "game_en.properties";
                break;
            default:
                file = "";
        }

        if(!file.equals("")) {
            try {

                FileInputStream fis = new FileInputStream(file);
                Properties pro = new Properties();
                pro.load(fis);

                YOUR_TURN = pro.getProperty("your_turn");
                OPPONENT_TURN = pro.getProperty("opponent_turn");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
