package ensi.sound;

import ensi.Client;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class Sounds {

    public static Sounds player;

    public MediaPlayer music;

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
            case VICTORY:
                fileName="victory.wav";
                break;
            default:
                break;
        }

        if(!fileName.equals("")){
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

    public void startMusic(){

        if (music == null) {
            URL resource = getClass().getResource("/sounds/music.wav");
            if (resource == null) {
                System.out.println("Musique introuvable");
                return;
            }

            music = new MediaPlayer(new Media(resource.toString()));
            music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
            music.setVolume(0.1);
        }

        if(Client.config.music)
            music.play();
    }

    public void stopMusic(){

        if(music != null){
            music.stop();
        }

    }


}
