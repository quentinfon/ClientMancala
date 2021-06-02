package ensi;

import ensi.model.Joueur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputServeur.setText("localhost");
        inputPort.setText("2009");
    }


    @FXML
    public void handleConnect(){

        if (Client.socket != null) return;

        Client.joueur.setPseudo(inputPseudo.getText());

        try
        {
            String serveur = inputServeur.getText();
            int port = Integer.parseInt(inputPort.getText());

            Client.socket = new Socket(serveur,port);

            System.out.println("Connexion avec l'utilisateur : " + Client.joueur);
            OutputStream os = Client.socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(Client.joueur);

            Client.screenController.activate("game");
            Client.clientThread = new ClientThread();


        } catch (NumberFormatException e){
            errMessage.setText("Format du port incorrect !");
        }catch (IOException e){
            errMessage.setText("Impossible de se connecter au serveur !");
        }
    }

}
