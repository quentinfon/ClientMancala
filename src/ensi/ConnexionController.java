package ensi;

import ensi.model.Joueur;
import javafx.fxml.FXML;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ConnexionController {

    @FXML
    public void handleConnect(){
        Socket socket;
        Joueur joueur = new Joueur();

        try
        {
            socket = new Socket(InetAddress.getLocalHost(),2009);
            System.out.println("Demande de connexion");
            InputStream is=socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);

            Client.joueur = (Joueur) ois.readObject();// envoie de l'objet
            Client.joueur.afficher();

            socket.close();

        } catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
    }

}
