package ensi;

import ensi.controller.GameController;
import ensi.model.GameData;
import ensi.model.InstructionModel;
import javafx.application.Platform;

import java.io.*;

public class ClientThread implements Runnable {

    private Thread _threadClient;

    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public ClientThread()
    {

        try
        {

            oos = new ObjectOutputStream(Client.socket.getOutputStream());
            ois = new ObjectInputStream(Client.socket.getInputStream());

            //Envoie du joueur au serveur
            oos.writeObject(Client.joueur);

        }
        catch (IOException e){ }

        _threadClient = new Thread(this);
        _threadClient.start();
    }


    public void run()
    {

        System.out.println("Debut du Thread client");

        try
        {

            while(true)
            {
                Object data = ois.readObject();

                if(data instanceof GameData){
                    Platform.runLater(() -> {
                        GameController.controller.displayGame((GameData) data);
                    });
                } else if (data instanceof InstructionModel){
                    Platform.runLater(() -> {
                        GameController.controller.server_instruction_stream((InstructionModel) data);
                    });
                } else {
                    System.out.println("Format de données recu inconnu");
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally
                {
                    try
                    {
                        System.out.println("Fin du Thread client");
                        if (Client.socket != null){
                            Client.socket.close();
                            Client.socket = null;
                        }

            }
            catch (IOException e){ }
        }
    }


}
