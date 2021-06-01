package ensi;

import ensi.model.Joueur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
        Socket socket;
        Joueur joueur = new Joueur();

        try
        {
            String serveur = inputServeur.getText();
            int port = Integer.parseInt(inputPort.getText());

            socket = new Socket(serveur,port);

            System.out.println("Demande de connexion");
            InputStream is=socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);

            Client.joueur = (Joueur) ois.readObject();
            Client.joueur.afficher();

            socket.close();

        } catch (NumberFormatException e){
            errMessage.setText("Format du port incorrect !");
        }catch (IOException e){
            errMessage.setText("Impossible de se connecter au serveur !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
