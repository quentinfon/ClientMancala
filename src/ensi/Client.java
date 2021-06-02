package ensi;
import ensi.controller.ScreenController;
import ensi.model.Joueur;
import ensi.trad.Traduction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.Socket;
import java.util.Locale;


public class Client extends Application {

    public static Stage stage;
    public static Joueur joueur;
    public static Socket socket;
    public static ClientThread clientThread;
    public static ScreenController screenController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        joueur = new Joueur();
        stage = primaryStage;

        //Set default language
        Traduction.setLanguage(Locale.FRENCH);

        Scene scene = new Scene(new AnchorPane(), 1080, 720);

        ScreenController screenController = new ScreenController(scene);

        screenController.addScreen("login", FXMLLoader.load(getClass().getResource( "/connexion.fxml" )));
        screenController.addScreen("game", FXMLLoader.load(getClass().getResource( "/plateauJeu.fxml" )));

        primaryStage.setScene(scene);
        primaryStage.setTitle("MANCALA");
        primaryStage.show();

        screenController.activate("login");
        Client.screenController = screenController;
    }

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

}
