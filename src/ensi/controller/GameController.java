package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.Utils;
import ensi.model.*;
import ensi.trad.Traduction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    private boolean reverseClient = false;

    @FXML
    public Label pseudoJoueur1, pseudoJoueur2;

    @FXML
    public Circle statusJoueur1, statusJoueur2;

    @FXML
    public Label scoreJoueur1, scoreJoueur2;


    /*Score on the board*/
    @FXML
    public Label scoreCase1J1, scoreCase2J1, scoreCase3J1, scoreCase4J1, scoreCase5J1, scoreCase6J1;
    @FXML
    public Label scoreCase1J2, scoreCase2J2, scoreCase3J2, scoreCase4J2, scoreCase5J2, scoreCase6J2;


    /*Playable indicator*/
    @FXML
    public Circle playable0, playable1, playable2, playable3, playable4, playable5;


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

    @FXML
    public void sendAction(Action action){
        //Redirect to login if not connected
        if (Client.socket == null){
            Client.screenController.activate("login");
            return;
        }

        try {
            ClientThread.oos.writeObject(new Commande(action));
        } catch (IOException | MissingNumToPlayException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void newGame(){
        sendAction(Action.NEW_GAME);
    }

    @FXML
    public void saveGame(){
        sendAction(Action.SAVE_GAME);
    }

    @FXML
    public void loadGame(){
        sendAction(Action.LOAD_GAME);
    }


    @FXML
    public void handlePlay(MouseEvent event)
    {
        int id = Integer.parseInt(((Circle)event.getSource()).getId().split("playable")[1]);
        //Reverse button order
        if(reverseClient){
            id = Math.abs(id-5);
        }
        System.out.println(id);
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

    /**
     * Server request (new game ...)
     * @param data the request
     */
    public void server_request(InstructionModel data){

        String demande = "";

        switch (data.instruction){
            case NEW_GAME:
                demande = Utils.firstLetterToUpper(Traduction.get("new_game_request"));
                break;
            default:
                return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, demande, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();

        InstructionModel reponse;

        if (alert.getResult() == ButtonType.YES) {
            reponse = new InstructionModel(Instruction.YES);
        }else{
            reponse = new InstructionModel(Instruction.NO);
        }

        try {
            ClientThread.oos.writeObject(reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Server info (player disconnect ...)
     * @param data the request
     */
    public void server_info(InstructionModel data){

        String message = "";

        switch (data.instruction){
            case OPPONENT_DISCONNECT:
                message = Utils.firstLetterToUpper(Traduction.get("opponent_disconnect"));
                break;
            default:
                return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        alert.showAndWait();


    }

    /**
     * Server instruction stream
     * @param data the instruction
     */
    public void server_instruction_stream(InstructionModel data){

        if (data.instruction == Instruction.NEW_GAME || data.instruction == Instruction.SURRENDER ||data.instruction == Instruction.LOAD_GAME){
            server_request(data);
        } else {
            server_info(data);
        }

    }


}
