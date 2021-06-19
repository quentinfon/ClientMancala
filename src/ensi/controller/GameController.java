package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.Utils;
import ensi.model.*;
import ensi.sound.SoudEvent;
import ensi.sound.Sounds;
import ensi.trad.Traduction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    public Label scoreJoueur1, scoreJoueur2, victoriesJoueur1, victoriesJoueur2;


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
    public MenuItem menuNewGame, menuSaveGame, menuLoadGame, menuUndo, menuAbout, langFR, langEN, menuSurrender, menuRules;

    @FXML
    public StackPane case1J2, case2J2, case3J2, case4J2, case5J2, case6J2;
    @FXML
    public StackPane case1J1, case2J1, case3J1, case4J1, case5J1, case6J1;


    @FXML
    public Label turnInfo;

    @FXML
    public Button splitLastPoints;


    @FXML
    public RadioMenuItem seedNumberOnHover, allSeedNumbers, allowSounds, allowMusic;


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

    @FXML
    public void surrender(){
        sendAction(Action.SURRENDER);
    }

    @FXML
    public void undoMove() {sendAction(Action.UNDO_MOVE);}

    @FXML
    public void handleSplitPoints() {sendAction(Action.SPLIT_LAST_POINTS);}



    @FXML
    public void showRules(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, Traduction.get("rules_of_the_game"));
        alert.setHeaderText(Utils.firstLetterToUpper(Traduction.get("rules")));
        alert.showAndWait();
    }

    @FXML
    public void showAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, Traduction.get("credits"));
        alert.setHeaderText("MANCALA v1.0");
        alert.showAndWait();
    }

    @FXML
    public void enterCell(MouseEvent mouseEvent) {
        if(Client.config.displaySeedNumbersOnHover){
            for (Node nodeIn : ((StackPane) mouseEvent.getSource()).getChildren()){
                if (nodeIn instanceof Label){
                    nodeIn.setOpacity(1);
                }
            }
        }
    }

    @FXML
    public void exitCell(MouseEvent mouseEvent) {
        if(Client.config.displaySeedNumbersOnHover && !Client.config.displayAllSeeds){
            for (Node nodeIn : ((StackPane) mouseEvent.getSource()).getChildren()){
                if (nodeIn instanceof Label){
                    nodeIn.setOpacity(0);
                }
            }
        }
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

        if(gameData != null && gameData.joueurs[0] != null){
            int indexClient = gameData.joueurs[0].equals(Client.joueur) ? 0 : 1;
            if (gameData.cases[indexClient][id] > 0 && gameData.playerTurn == indexClient){
                Sounds.getPlayer().play(SoudEvent.PLAY);
            } else {
                Sounds.getPlayer().play(SoudEvent.NOT_PLAYABLE);
            }
        }else {
            Sounds.getPlayer().play(SoudEvent.PLAY);
        }

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


    /**
     * Return the points left to claim
     * @param cells the game board
     * @return the number of point left
     */
    private int sumLeftPoint(int[][] cells){

        int count = 0;
        try{
            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 6; j++){
                    count += cells[i][j];
                }
            }
            return count;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * Display the game
     * @param data the game data
     */
    public void displayGame(GameData data){

        gameData = data;
        System.out.println(data);

        //Display split button
        int leftPoints = sumLeftPoint(data.cases);
        if (leftPoints != -1 && leftPoints < 10){
            splitLastPoints.setText(Traduction.get("split_points_button"));
            splitLastPoints.setDisable(false);
            splitLastPoints.setOpacity(1);
        }else {
            splitLastPoints.setDisable(true);
            splitLastPoints.setOpacity(0);
        }

        //Set players status
        if(data.joueurs[0] != null){
            int indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

            //Display players turn
            if (data.joueurs[1] != null){
                if (indexClient == data.playerTurn){
                    turnInfo.setText(Traduction.get("your_turn"));
                    turnInfo.setTextFill(Color.GREEN);

                    menuSurrender.setDisable(false);
                    menuUndo.setDisable(true);
                } else {
                    turnInfo.setText(Traduction.get("opponent_turn"));
                    turnInfo.setTextFill(Color.RED);

                    splitLastPoints.setDisable(true);
                    menuSurrender.setDisable(true);
                    menuUndo.setDisable(false);
                }
            } else {
                turnInfo.setText("");
            }



            pseudoJoueur1.setText(data.joueurs[indexClient].pseudo);
            statusJoueur1.setFill(data.joueurs[indexClient].connected ? Color.GREEN : Color.RED);
            scoreJoueur1.setText(Traduction.get("score")+" : "+data.scores[indexClient]);
            victoriesJoueur1.setText(Traduction.get("victories")+" : "+data.victories[indexClient]+ "/ 6");


            if (data.joueurs[indexClient == 0 ? 1 : 0] != null) {
                pseudoJoueur2.setText(data.joueurs[indexClient == 0 ? 1 : 0].pseudo);
                statusJoueur2.setFill(data.joueurs[indexClient == 0 ? 1 : 0].connected ? Color.GREEN : Color.RED);
                scoreJoueur2.setText(Traduction.get("score")+" : "+data.scores[indexClient == 0 ? 1 : 0]);
                victoriesJoueur2.setText(Traduction.get("victories")+" : "+data.victories[indexClient == 0 ? 1 : 0] + "/ 6");
                //Indicateur cases jouable
                if (data.cases != null)
                    setPlayableDisplay(indexClient == data.playerTurn, data.cases[indexClient]);

            }else{
                pseudoJoueur2.setText(Traduction.get("waiting_another_player"));
                statusJoueur2.setFill(Color.ORANGE);
            }

        }else{
            //If waiting for an opponent
            pseudoJoueur1.setText(Client.joueur.pseudo);
            statusJoueur1.setFill(Color.ORANGE);

            pseudoJoueur2.setText(Traduction.get("waiting_another_player"));
            statusJoueur2.setFill(Color.ORANGE);

        }


        if(data.cases != null){
            int indexClient = 1;

            //Set the client in reverse if client is the second player
            if (data.joueurs[0] != null){
                indexClient = data.joueurs[0].equals(Client.joueur) ? 0 : 1;

                if (indexClient == 0) {
                    reverseClient = true;
                }else{
                    reverseClient = false;
                }
            }


            scoreCase1J1.setText(data.cases[indexClient][reverseClient ? 5 : 0] + "");
            scoreCase2J1.setText(data.cases[indexClient][reverseClient ? 4 : 1] + "");
            scoreCase3J1.setText(data.cases[indexClient][reverseClient ? 3 : 2] + "");
            scoreCase4J1.setText(data.cases[indexClient][reverseClient ? 2 : 3] + "");
            scoreCase5J1.setText(data.cases[indexClient][reverseClient ? 1 : 4] + "");
            scoreCase6J1.setText(data.cases[indexClient][reverseClient ? 0 : 5] + "");

            scoreCase1J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 5 : 0] + "");
            scoreCase2J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 4 : 1] + "");
            scoreCase3J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 3 : 2] + "");
            scoreCase4J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 2 : 3] + "");
            scoreCase5J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 1 : 4] + "");
            scoreCase6J2.setText(data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 0 : 5] + "");

            //Display points on each cells
            scoreCase1J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase2J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase3J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase4J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase5J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase6J1.setOpacity(Client.config.displayAllSeeds ? 1 : 0);

            scoreCase1J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase2J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase3J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase4J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase5J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);
            scoreCase6J2.setOpacity(Client.config.displayAllSeeds ? 1 : 0);



            setPionsImg(case1J1, data.cases[indexClient][reverseClient ? 5 : 0], -75, 1, 0);
            setPionsImg(case2J1, data.cases[indexClient][reverseClient ? 4 : 1], -75, 1, 1);
            setPionsImg(case3J1, data.cases[indexClient][reverseClient ? 3 : 2], -75, 1, 2);
            setPionsImg(case4J1, data.cases[indexClient][reverseClient ? 2 : 3], -75, 1, 3);
            setPionsImg(case5J1, data.cases[indexClient][reverseClient ? 1 : 4], -75, 1, 4);
            setPionsImg(case6J1, data.cases[indexClient][reverseClient ? 0 : 5], -75, 1, 5);

            setPionsImg(case1J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 5 : 0], 0, 2, 0);
            setPionsImg(case2J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 4 : 1], 0, 2, 1);
            setPionsImg(case3J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 3 : 2], 0, 2, 2);
            setPionsImg(case4J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 2 : 3], 0, 2, 3);
            setPionsImg(case5J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 1 : 4], 0, 2, 4);
            setPionsImg(case6J2, data.cases[indexClient == 0 ? 1 : 0][reverseClient ? 0 : 5], 0, 2, 5);


        }

    }


    /**
     * Set the img to the cell base on the number
     * @param pane stackpane
     * @param nbPions number
     */
    public void setPionsImg(StackPane pane, int nbPions, int yOffset, int player, int cell){

        if (listeDesPions.containsKey(pane)){
            pane.getChildren().removeAll(listeDesPions.get(pane));
            listeDesPions.remove(pane);
        }

        ArrayList<CoordoneesPionts> coordonees = CoordoneesPionts.randomCoordonnees(player, cell);

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
            case SAVE_GAME:
                demande = Utils.firstLetterToUpper(Traduction.get("save_request"));
                break;
            case LOAD_GAME:
                demande = Utils.firstLetterToUpper(Traduction.get("load_request"));
                break;
            case SPLIT_LAST_POINTS:
                demande = Utils.firstLetterToUpper(Traduction.get("surrender_request"));
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

        StringBuilder message = new StringBuilder();

        switch (data.instruction){
            case OPPONENT_DISCONNECT:
                message = new StringBuilder(Utils.firstLetterToUpper(Traduction.get("opponent_disconnect")));
                break;
            case END_OF_GAME:
                if(data.data.equals("")){
                    message = new StringBuilder(Traduction.get("draw_game"));
                }else{
                    if(data.data.equals(Client.joueur.id)){
                        message = new StringBuilder(Traduction.get("victory_game"));
                    } else {
                        message = new StringBuilder(Traduction.get("defeat_game"));
                    }
                }
                break;
            case END_OF_MATCH:
                if(data.data.equals("")){
                    message = new StringBuilder(Traduction.get("draw_match"));
                }else{
                    if(data.data.equals(Client.joueur.id)){
                        message = new StringBuilder(Traduction.get("victory_match"));
                    } else {
                        message = new StringBuilder(Traduction.get("defeat_match"));
                    }
                }
                break;
            case UNDO_MOVE:
                message = new StringBuilder(Traduction.get("opponent_undo_move"));
                break;
            default:
                return;
        }

        if (data.scores != null) {
            message.append("\n\n\n").append(Traduction.get("best_scores")).append(" : \n\n");
            for (Score score : data.scores){
                message.append(" - ").append(score.winnerName).append(" : ").append(score.score).append("\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, message.toString());
        alert.setHeaderText(null);
        alert.showAndWait();


    }

    /**
     * Server instruction stream
     * @param data the instruction
     */
    public void server_instruction_stream(InstructionModel data){

        if (data.instruction == Instruction.NEW_GAME|| data.instruction == Instruction.SAVE_GAME || data.instruction == Instruction.SPLIT_LAST_POINTS ||data.instruction == Instruction.LOAD_GAME){
            server_request(data);
        } else {
            server_info(data);
        }

    }


    @FXML
    public void handleSeedNumberOnHover(){
        Client.config.setDisplaySeedNumbersOnHover(seedNumberOnHover.isSelected());
    }

    @FXML
    public void handleAllSeedNumbers(){
        Client.config.setDisplayAllSeeds(allSeedNumbers.isSelected());
        displayGame(gameData);
    }

    @FXML
    public void handleAllowSounds(){
        Client.config.setSounds(allowSounds.isSelected());
    }

    @FXML
    public void handleAllowMusic(){
        Client.config.setMusic(allowMusic.isSelected());
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
        menuSurrender.setText(Utils.firstLetterToUpper(Traduction.get("surrender")));


        menuParameter.setText(Utils.firstLetterToUpper(Traduction.get("parameter")));
        seedNumberOnHover.setText(Utils.firstLetterToUpper(Traduction.get("seed_on_hover")));
        allSeedNumbers.setText(Utils.firstLetterToUpper(Traduction.get("all_seeds")));
        allowSounds.setText(Utils.firstLetterToUpper(Traduction.get("sounds")));
        allowMusic.setText(Utils.firstLetterToUpper(Traduction.get("music")));

        seedNumberOnHover.setSelected(Client.config.displaySeedNumbersOnHover);
        allSeedNumbers.setSelected(Client.config.displayAllSeeds);
        allowSounds.setSelected(Client.config.sounds);
        allowMusic.setSelected(Client.config.music);


        menuLangues.setText(Utils.firstLetterToUpper(Traduction.get("language")));


        langFR.setText(Utils.firstLetterToUpper(Traduction.get("french") + selectedLanguage("fr")));
        langEN.setText(Utils.firstLetterToUpper(Traduction.get("english") + selectedLanguage("en")));


        menuHelp.setText(Utils.firstLetterToUpper(Traduction.get("help")));
        menuAbout.setText(Utils.firstLetterToUpper(Traduction.get("about")));
        menuRules.setText(Utils.firstLetterToUpper(Traduction.get("rules")));


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
