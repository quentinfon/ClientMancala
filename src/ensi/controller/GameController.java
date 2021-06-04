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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static GameController controller;

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
    public void handlePlay(MouseEvent event)
    {
        String id = ((StackPane)event.getSource()).getId().split("play")[1];
        play(Integer.parseInt(id));
    }


    public void displayGame(GameData data){

        System.out.println(data);

        //Set players status
        if(data.joueurs[0] != null){
            int indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

            pseudoJoueur1.setText(data.joueurs[indexClient].pseudo);
            statusJoueur1.setFill(data.joueurs[indexClient].connected ? Color.GREEN : Color.RED);

            if (data.joueurs[indexClient == 0 ? 1 : 0] != null) {
                pseudoJoueur2.setText(data.joueurs[indexClient == 0 ? 1 : 0].pseudo);
                statusJoueur2.setFill(data.joueurs[indexClient == 0 ? 1 : 0].connected ? Color.GREEN : Color.RED);
            }else{
                pseudoJoueur2.setText("En attente...");
                statusJoueur2.setFill(Color.ORANGE);
            }

        }else{

            pseudoJoueur1.setText(Client.joueur.pseudo);
            statusJoueur1.setFill(Color.ORANGE);

            pseudoJoueur2.setText("En attente...");
            statusJoueur2.setFill(Color.ORANGE);

        }


    }

}
