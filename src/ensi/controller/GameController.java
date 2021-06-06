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


    /*Score on the board*/
    @FXML
    public Label scoreCase1J1;
    @FXML
    public Label scoreCase2J1;
    @FXML
    public Label scoreCase3J1;
    @FXML
    public Label scoreCase4J1;
    @FXML
    public Label scoreCase5J1;
    @FXML
    public Label scoreCase6J1;
    @FXML
    public Label scoreCase1J2;
    @FXML
    public Label scoreCase2J2;
    @FXML
    public Label scoreCase3J2;
    @FXML
    public Label scoreCase4J2;
    @FXML
    public Label scoreCase5J2;
    @FXML
    public Label scoreCase6J2;

    /*Playable indicator*/
    @FXML
    public Circle playable0;
    @FXML
    public Circle playable1;
    @FXML
    public Circle playable2;
    @FXML
    public Circle playable3;
    @FXML
    public Circle playable4;
    @FXML
    public Circle playable5;


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

    public void setPlayableDisplay(boolean playerTurn, int[] playerCells){
        if (playerTurn){
            playable0.setOpacity(playerCells[0] > 0 ? 1 : 0);
            playable1.setOpacity(playerCells[1] > 0 ? 1 : 0);
            playable2.setOpacity(playerCells[2] > 0 ? 1 : 0);
            playable3.setOpacity(playerCells[3] > 0 ? 1 : 0);
            playable4.setOpacity(playerCells[4] > 0 ? 1 : 0);
            playable5.setOpacity(playerCells[5] > 0 ? 1 : 0);
        }else{
            playable0.setOpacity(0);
            playable1.setOpacity(0);
            playable2.setOpacity(0);
            playable3.setOpacity(0);
            playable4.setOpacity(0);
            playable5.setOpacity(0);
        }
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

                //Indicateur cases jouable
                if (data.cases != null)
                    setPlayableDisplay(indexClient == data.playerTurn, data.cases[indexClient]);

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


        if(data.cases != null){
            int indexClient = 0;
            if (data.joueurs[0] != null)
                indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

            scoreCase1J1.setText(data.cases[indexClient][0]+"");
            scoreCase2J1.setText(data.cases[indexClient][1]+"");
            scoreCase3J1.setText(data.cases[indexClient][2]+"");
            scoreCase4J1.setText(data.cases[indexClient][3]+"");
            scoreCase5J1.setText(data.cases[indexClient][4]+"");
            scoreCase6J1.setText(data.cases[indexClient][5]+"");

            scoreCase1J2.setText(data.cases[indexClient == 0 ? 1 : 0][0]+"");
            scoreCase2J2.setText(data.cases[indexClient == 0 ? 1 : 0][1]+"");
            scoreCase3J2.setText(data.cases[indexClient == 0 ? 1 : 0][2]+"");
            scoreCase4J2.setText(data.cases[indexClient == 0 ? 1 : 0][3]+"");
            scoreCase5J2.setText(data.cases[indexClient == 0 ? 1 : 0][4]+"");
            scoreCase6J2.setText(data.cases[indexClient == 0 ? 1 : 0][5]+"");

        }

    }

}
