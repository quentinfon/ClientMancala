package ensi.controller;

import ensi.Client;
import ensi.ClientThread;
import ensi.Utils;
import ensi.trad.Traduction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextPage();
        inputServeur.setText("localhost");
        inputPort.setText("2009");
    }

    private void setTextPage(){
        txtPseudo.setText(Utils.firstLetterToUpper(Traduction.get("pseudo")));
        txtConnexion.setText(Utils.firstLetterToUpper(Traduction.get("connection")) + " MANCALA");
        txtServerPort.setText(Utils.firstLetterToUpper(Traduction.get("server")) + " / " + Utils.firstLetterToUpper(Traduction.get("port")));
        loginBtn.setText(Utils.firstLetterToUpper(Traduction.get("connection")));
    }

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

            Client.screenController.activate("game");
            Client.clientThread = new ClientThread();

            System.out.println("ee");
            //Envoie du joueur au serveur
            ClientThread.oos.writeObject(Client.joueur);

            System.out.println("Test");


        } catch (NumberFormatException e){
            errMessage.setText("Format du port incorrect !");
        }catch (IOException e){
            errMessage.setText("Impossible de se connecter au serveur !");
        }
    }

}
