package ensi;

import ensi.controller.GameController;
import ensi.model.Commande;
import ensi.model.GameData;

import java.io.*;

public class ClientThread implements Runnable {

    private Thread _threadClient;

    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public ClientThread()
    {

        try
        {

            OutputStream os = Client.socket.getOutputStream();
            oos = new ObjectOutputStream(os);


            InputStream is = Client.socket.getInputStream();
            ois = new ObjectInputStream(is);


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
                GameData data = (GameData) ois.readObject();

                GameController.controller.displayGame(data);

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
