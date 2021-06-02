package ensi;

import ensi.model.Action;
import ensi.model.Commande;
import ensi.model.MissingNumToPlayException;
import javafx.fxml.FXML;

import java.io.IOException;

public class GameController {

    private int count = 0;

    public void play(int numCase){

        //Redirect to login if not connected
        if (Client.socket == null){
            Client.screenController.activate("login");
            return;
        }

        try {
            ClientThread.oos.writeObject(new Commande(Action.PLAY, numCase));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void newGame(){
        //Redirect to login if not connected
        if (Client.socket == null){
            Client.screenController.activate("login");
            return;
        }

        try {
            ClientThread.oos.writeObject(new Commande(Action.NEW_GAME));
        } catch (IOException | MissingNumToPlayException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleTest(){
        count++;
        play(count);
    }

}
