package ensi;

public class Utils {

    public static String firstLetterToUpper(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
