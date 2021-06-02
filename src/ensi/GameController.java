package ensi;

import ensi.model.Action;
import ensi.model.Commande;
import javafx.fxml.FXML;

import java.io.IOException;

public class GameController {

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

    @FXML
    public void handleTest(){
        System.out.println("test");
        play(5);
    }

}
