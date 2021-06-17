package ensi.sound;

import ensi.Client;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sounds {

    public static Sounds player;

    public static Sounds getPlayer(){
        if (player == null){
            player = new Sounds();
        }
        return player;
    }

    public void play(SoudEvent event){

        if (!Client.config.sounds) return;

        String fileName = "";

        switch (event){
            case PLAY:
                fileName="play.wav";
                break;
            case NOT_PLAYABLE:
                fileName="not_playable.wav";
                break;
            case DEFEAT:
                fileName="game_lost.wav";
                break;
            default:
                break;
        }

        if(!fileName.equals("")){
            System.out.println("sounds/" + fileName);
            URL resource = getClass().getResource("/sounds/" + fileName);
            if (resource == null) {
                System.out.println("Fichier introuvable");
                return;
            }

            Media sound = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }

    }

}
