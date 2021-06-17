package ensi.model;

import java.io.*;
import java.util.Locale;
import java.util.UUID;

public class ClientConfig implements Serializable {

    public Locale language;
    public boolean sounds;
    public boolean music;


    public ClientConfig(String clientID, Locale lang, boolean sounds, boolean music){
        this.language = lang;
        this.sounds = sounds;
        this.music = music;
    }


    public void setLanguage(Locale l){
        this.language = l;
        saveConfig();
    }

    public void setSounds(boolean sounds){
        this.sounds = sounds;
        saveConfig();
    }

    public void setMusic(boolean music){
        this.music = music;
        saveConfig();
    }


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


    public static ClientConfig getConfig(){

        //default config
        ClientConfig config = new ClientConfig(UUID.randomUUID().toString(), Locale.FRENCH, true, true);

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
