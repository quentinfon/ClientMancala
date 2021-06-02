package ensi;
import ensi.model.Joueur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.InetAddress;

import java.net.Socket;

import java.net.UnknownHostException;


public class Client extends Application {

    public static Stage stage;
    public static Joueur joueur;
    public static Socket socket;
    public static ClientThread clientThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        joueur = new Joueur();
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/connexion.fxml"));
        primaryStage.setTitle("MANCALA");

        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    @Override
    public void stop(){
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setSocket(Socket s){
        Client.socket = s;

    }
}
