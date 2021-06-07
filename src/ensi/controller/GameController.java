package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.model.Action;
import ensi.model.Commande;
import ensi.model.GameData;
import ensi.model.MissingNumToPlayException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static GameController controller;

    private boolean reverseClient = false;

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


    /*Action menu icon*/
    @FXML
    public ImageView actionMenu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;

        ContextMenu contextMenu = new ContextMenu();
        MenuItem test = new MenuItem("Test");
        MenuItem newGame = new MenuItem("Test");
        MenuItem loadGame = new MenuItem("Test");

        contextMenu.getItems().addAll(test, newGame, loadGame);
        actionMenu.setOnMouseClicked(e -> {
            System.out.println("test");
            contextMenu.show(actionMenu, e.getSceneX(), e.getSceneY());
        });

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
        int id = Integer.parseInt(((StackPane)event.getSource()).getId().split("play")[1]);
        //Reverse button order
        if(reverseClient){
            id = Math.abs(id-5);
        }
        play(id);
    }

    public void setPlayableDisplay(boolean playerTurn, int[] playerCells){
        if (playerTurn){
            playable0.setOpacity(playerCells[reverseClient ? 5 : 0] > 0 ? 1 : 0);
            playable1.setOpacity(playerCells[reverseClient ? 4 : 1] > 0 ? 1 : 0);
            playable2.setOpacity(playerCells[reverseClient ? 3 : 2] > 0 ? 1 : 0);
            playable3.setOpacity(playerCells[reverseClient ? 2 : 3] > 0 ? 1 : 0);
            playable4.setOpacity(playerCells[reverseClient ? 1 : 4] > 0 ? 1 : 0);
            playable5.setOpacity(playerCells[reverseClient ? 0 : 5] > 0 ? 1 : 0);
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
            scoreJoueur1.setText("Score : "+data.scores[indexClient]);

            if (data.joueurs[indexClient == 0 ? 1 : 0] != null) {
                pseudoJoueur2.setText(data.joueurs[indexClient == 0 ? 1 : 0].pseudo);
                statusJoueur2.setFill(data.joueurs[indexClient == 0 ? 1 : 0].connected ? Color.GREEN : Color.RED);
                scoreJoueur2.setText("Score : "+data.scores[indexClient == 0 ? 1 : 0]);

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
            int indexClient = 1;
            if (data.joueurs[0] != null){
                indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

                System.out.println("Index client : "+indexClient);

                if (indexClient == 0) {
                    reverseClient = true;
                }else{
                    reverseClient = false;
                }
            }

            if(reverseClient)
                System.out.println("Reverse client");


            scoreCase1J1.setText(data.cases[indexClient][reverseClient ? 5 : 0]+"");
            scoreCase2J1.setText(data.cases[indexClient][reverseClient ? 4 : 1]+"");
            scoreCase3J1.setText(data.cases[indexClient][reverseClient ? 3 : 2]+"");
            scoreCase4J1.setText(data.cases[indexClient][reverseClient ? 2 : 3]+"");
            scoreCase5J1.setText(data.cases[indexClient][reverseClient ? 1 : 4]+"");
            scoreCase6J1.setText(data.cases[indexClient][reverseClient ? 0 : 5]+"");

            scoreCase1J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 5 : 0]+"");
            scoreCase2J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 4 : 1]+"");
            scoreCase3J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 3 : 2]+"");
            scoreCase4J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 2 : 3]+"");
            scoreCase5J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 1 : 4]+"");
            scoreCase6J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 0 : 5]+"");

        }

    }

}
