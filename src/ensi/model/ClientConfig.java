package ensi.model;

import java.io.*;
import java.util.Locale;
import java.util.UUID;

public class ClientConfig implements Serializable {

    public Locale language;
    public boolean displaySeedNumbersOnHover;
    public boolean displayAllSeeds;
    public boolean sounds;
    public boolean music;


    public ClientConfig(String clientID, Locale lang, boolean sounds, boolean music, boolean displayAllSeeds, boolean displaySeedNumbersOnHover){
        this.language = lang;
        this.sounds = sounds;
        this.music = music;
        this.displayAllSeeds = displayAllSeeds;
        this.displaySeedNumbersOnHover = displaySeedNumbersOnHover;
    }

    /**
     * Set the language of the config
     * @param l the language
     */
    public void setLanguage(Locale l){
        this.language = l;
        saveConfig();
    }

    /**
     * Set if sounds is on
     * @param sounds true : on else false
     */
    public void setSounds(boolean sounds){
        this.sounds = sounds;
        saveConfig();
    }

    /**
     * Set if music is on
     * @param music true : on else false
     */
    public void setMusic(boolean music){
        this.music = music;
        saveConfig();
    }

    /**
     * Set seed number on hover
     * @param s true or flase
     */
    public void setDisplaySeedNumbersOnHover(boolean s){
        this.displaySeedNumbersOnHover = s;
        saveConfig();
    }

    /**
     * Set display all seed numbers
     * @param s true or flase
     */
    public void setDisplayAllSeeds(boolean s){
        this.displayAllSeeds = s;
        saveConfig();
    }

    /**
     * Save the config on a file
     */
    public void saveConfig(){
        File file = new File("mancala.config");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the config
     * @return the config
     */
    public static ClientConfig getConfig(){

        //default config
        ClientConfig config = new ClientConfig(UUID.randomUUID().toString(), Locale.FRENCH, true, true, false, true);

        File file = new File("mancala.config");

        if(file.exists()){

            FileInputStream fis = null;
            ObjectInputStream ois = null;

            try {

                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                config = (ClientConfig) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else{
            config.saveConfig();
        }

        return config;
    }

}
