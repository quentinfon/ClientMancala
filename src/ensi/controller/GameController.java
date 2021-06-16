package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.Utils;
import ensi.model.*;
import ensi.trad.Traduction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static GameController controller;

    private GameData gameData;

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


    @FXML
    public Menu menuPartie, menuParameter, menuHelp, menuLangues;

    @FXML
    public MenuItem menuNewGame, menuSaveGame, menuLoadGame, menuUndo, menuAbout, langFR, langEN;

    @FXML
    public StackPane case1J2, case2J2, case3J2, case4J2, case5J2, case6J2;
    @FXML
    public StackPane case1J1, case2J1, case3J1, case4J1, case5J1, case6J1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = this;
        refreshViewLanguage();
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


    private HashMap<StackPane, ArrayList<Circle>> listeDesPions = new HashMap<>();


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

    @FXML
    public void setFrench(){
        Traduction.changeLanguage("FR");
        refreshViewLanguage();
    }

    @FXML
    public void setEnglish(){
        Traduction.changeLanguage("EN");
        refreshViewLanguage();
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

        gameData = data;
        System.out.println(data);

        //Set players status
        if(data.joueurs[0] != null){
            int indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;


            pseudoJoueur1.setText(data.joueurs[indexClient].pseudo);
            statusJoueur1.setFill(data.joueurs[indexClient].connected ? Color.GREEN : Color.RED);
            scoreJoueur1.setText(Traduction.get("score")+" : "+data.scores[indexClient]);

            if (data.joueurs[indexClient == 0 ? 1 : 0] != null) {
                pseudoJoueur2.setText(data.joueurs[indexClient == 0 ? 1 : 0].pseudo);
                statusJoueur2.setFill(data.joueurs[indexClient == 0 ? 1 : 0].connected ? Color.GREEN : Color.RED);
                scoreJoueur2.setText(Traduction.get("score")+" : "+data.scores[indexClient == 0 ? 1 : 0]);

                //Indicateur cases jouable
                if (data.cases != null)
                    setPlayableDisplay(indexClient == data.playerTurn, data.cases[indexClient]);

            }else{
                pseudoJoueur2.setText(Traduction.get("waiting_another_player"));
                statusJoueur2.setFill(Color.ORANGE);
            }

        }else{

            pseudoJoueur1.setText(Client.joueur.pseudo);
            statusJoueur1.setFill(Color.ORANGE);

            pseudoJoueur2.setText(Traduction.get("waiting_another_player"));
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


            setPionsImg(case1J1, data.cases[indexClient][reverseClient ? 5 : 0], -75);
            setPionsImg(case2J1, data.cases[indexClient][reverseClient ? 4 : 1], -75);
            setPionsImg(case3J1, data.cases[indexClient][reverseClient ? 3 : 2], -75);
            setPionsImg(case4J1, data.cases[indexClient][reverseClient ? 2 : 3], -75);
            setPionsImg(case5J1, data.cases[indexClient][reverseClient ? 1 : 4], -75);
            setPionsImg(case6J1, data.cases[indexClient][reverseClient ? 0 : 5], -75);

            setPionsImg(case1J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 5 : 0], 0);
            setPionsImg(case2J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 4 : 1], 0);
            setPionsImg(case3J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 3 : 2], 0);
            setPionsImg(case4J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 2 : 3], 0);
            setPionsImg(case5J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 1 : 4], 0);
            setPionsImg(case6J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 0 : 5], 0);


        }

    }


    /**
     * Set the img to the cell base on the number
     * @param pane stackpane
     * @param nbPions number
     */
    public void setPionsImg(StackPane pane, int nbPions, int yOffset){

        if (listeDesPions.containsKey(pane)){
            pane.getChildren().removeAll(listeDesPions.get(pane));
            listeDesPions.remove(pane);
        }

        ArrayList<CoordoneesPionts> coordonees = CoordoneesPionts.listeCoordonnees();

        ArrayList<Circle> pions = new ArrayList<>();

        for (int i = 0; i < nbPions; i++){
            if(i < coordonees.size()){
                CoordoneesPionts placement = coordonees.get(i);

                Circle pion = new Circle();
                pion.setFill(Color.WHITE);
                pion.setRadius(8);

                pion.setTranslateX(placement.decalageX);
                pion.setTranslateY(placement.decalageY + yOffset);
                pane.getChildren().add(pion);
                pions.add(pion);
            }
        }

        listeDesPions.put(pane, pions);

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


    /**
     * Set the client language
     */
    public void refreshViewLanguage(){

        menuPartie.setText(Utils.firstLetterToUpper(Traduction.get("game")));
        menuNewGame.setText(Utils.firstLetterToUpper(Traduction.get("new_game")));
        menuSaveGame.setText(Utils.firstLetterToUpper(Traduction.get("save_game")));
        menuLoadGame.setText(Utils.firstLetterToUpper(Traduction.get("load_game")));
        menuUndo.setText(Utils.firstLetterToUpper(Traduction.get("undo_move")));

        menuParameter.setText(Utils.firstLetterToUpper(Traduction.get("parameter")));


        menuLangues.setText(Utils.firstLetterToUpper(Traduction.get("language")));


        langFR.setText(Utils.firstLetterToUpper(Traduction.get("french") + selectedLanguage("fr")));
        langEN.setText(Utils.firstLetterToUpper(Traduction.get("english") + selectedLanguage("en")));


        menuHelp.setText(Utils.firstLetterToUpper(Traduction.get("help")));
        menuAbout.setText(Utils.firstLetterToUpper(Traduction.get("about")));

        if(gameData != null)
            displayGame(gameData);

    }

    private String selectedLanguage(String language){
        if(Traduction.getLanguage().equals(language)){
            return " - X";
        }
        return "";
    }



}
