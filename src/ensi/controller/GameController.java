package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.model.Action;
import ensi.model.Commande;
import ensi.model.GameData;
import ensi.model.MissingNumToPlayException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static GameController controller;

    private int count = 0;

    @FXML
    public Label pseudoJoueur1;

    @FXML
    public Label pseudoJoueur2;

    @FXML
    public Circle statusJoueur1;

    @FXML
    public Circle statusJoueur2;

    @FXML
    public Label scoreJoueur1;

    @FXML
    public Label scoreJoueur2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;
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

    public void displayGame(GameData data){

        //Set players status
        int indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

        pseudoJoueur1.setText(data.joueurs[indexClient].pseudo);
        pseudoJoueur2.setText(data.joueurs[indexClient == 0 ? 1 : 0].pseudo);

        statusJoueur1.setFill(data.joueurs[indexClient].connected ? Color.GREEN : Color.RED);
        statusJoueur1.setFill(data.joueurs[indexClient == 0 ? 1 : 0].connected ? Color.GREEN : Color.RED);

    }

}
