package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.model.Action;
import ensi.model.Commande;
import ensi.model.MissingNumToPlayException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private int count = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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


    public void handlePlayOne(MouseEvent mouseEvent) {
        play(1);
    }

    public void handlePlayTwo(MouseEvent mouseEvent) {
        play(2);
    }

    public void handlePlayThree(MouseEvent mouseEvent) {
        play(3);
    }

    public void handlePlayFor(MouseEvent mouseEvent) {
        play(4);
    }

    public void handlePlayFive(MouseEvent mouseEvent) {
        play(5);
    }

    public void handlePlaySix(MouseEvent mouseEvent) {
        play(6);
    }

}
