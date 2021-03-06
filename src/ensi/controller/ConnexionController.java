package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.Utils;
import ensi.sound.Sounds;
import ensi.trad.Traduction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {

    @FXML
    public Label errMessage;

    @FXML
    public TextField inputPseudo;

    @FXML
    public TextField inputServeur;

    @FXML
    public TextField inputPort;

    @FXML
    public Label txtPseudo;

    @FXML
    public Label txtServerPort;

    @FXML
    public Label txtConnexion;

    @FXML
    public Button loginBtn;

    @FXML
    public Menu menuHelp, menuLangues;

    @FXML
    public MenuItem menuAbout, langFR, langEN;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshViewLanguage();
        inputServeur.setText("localhost");
        inputPort.setText("2009");
    }


    /**
     * Function to connect to the server
     */
    @FXML
    public void handleConnect(){

        if (Client.socket != null) return;

        Client.joueur.setPseudo(inputPseudo.getText());

        try
        {
            String serveur = inputServeur.getText();
            int port = Integer.parseInt(inputPort.getText());

            System.out.println("Test");
            Client.socket = new Socket(serveur,port);

            System.out.println("Connexion avec l'utilisateur : " + Client.joueur);

            Client.clientThread = new ClientThread();
            Client.screenController.activate("game");
            Sounds.getPlayer().startMusic();
            GameController.controller.refreshViewLanguage();

        } catch (NumberFormatException e){
            errMessage.setText("Format du port incorrect !");
        }catch (IOException e){
            errMessage.setText("Impossible de se connecter au serveur !");
        }
    }

    /**
     * Set language french
     */
    @FXML
    public void setFrench(){
        Traduction.changeLanguage("FR");
        Client.config.setLanguage(Locale.FRENCH);
        refreshViewLanguage();
    }

    /**
     * Set language english
     */
    @FXML
    public void setEnglish(){
        Traduction.changeLanguage("EN");
        Client.config.setLanguage(Locale.ENGLISH);
        refreshViewLanguage();
    }

    /**
     * Show about alert
     */
    @FXML
    public void showAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, Traduction.get("credits"));
        alert.setHeaderText("MANCALA v1.0");
        alert.showAndWait();
    }


    /**
     * Set the client language
     */
    public void refreshViewLanguage(){

        txtPseudo.setText(Utils.firstLetterToUpper(Traduction.get("pseudo")));
        txtConnexion.setText(Utils.firstLetterToUpper(Traduction.get("connection")) + " MANCALA");
        txtServerPort.setText(Utils.firstLetterToUpper(Traduction.get("server")) + " / " + Utils.firstLetterToUpper(Traduction.get("port")));
        loginBtn.setText(Utils.firstLetterToUpper(Traduction.get("connection")));

        menuLangues.setText(Utils.firstLetterToUpper(Traduction.get("language")));

        langFR.setText(Utils.firstLetterToUpper(Traduction.get("french") + selectedLanguage("fr")));
        langEN.setText(Utils.firstLetterToUpper(Traduction.get("english") + selectedLanguage("en")));


        menuHelp.setText(Utils.firstLetterToUpper(Traduction.get("help")));
        menuAbout.setText(Utils.firstLetterToUpper(Traduction.get("about")));
    }

    /**
     * Return custom string if selected language
     * @param language the language selected
     * @return custom string
     */
    private String selectedLanguage(String language){
        if(Traduction.getLanguage().equals(language)){
            return " - X";
        }
        return "";
    }

}
